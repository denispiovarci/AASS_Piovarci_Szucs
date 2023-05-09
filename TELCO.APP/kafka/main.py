# pip install confluent_kafka

import json

from confluent_kafka.admin import AdminClient, NewTopic
from confluent_kafka import Producer, KafkaException, Consumer

boostrap_servers = 'localhost:9092'
consumer_group_id = 'MyConsumerGroup12'
auto_offset_reset = 'earliest'

# Configure admin client
admin_client = AdminClient({
    'bootstrap.servers': boostrap_servers
})

# Configure producer client
producer_client = Producer({
    'bootstrap.servers': boostrap_servers
})

consumer_client = Consumer({
    'bootstrap.servers': boostrap_servers,
    'group.id': consumer_group_id,
    'auto.offset.reset': auto_offset_reset
})


def create_topic():
    topics_list = []
    topic_name = 'events'
    partitions_count = 1
    replication_factor = 1
    topics_list.append(NewTopic(topic_name, partitions_count, replication_factor))

    print('Creating topics with configuration:\n')
    print(*topics_list, sep="\n")

    fs = admin_client.create_topics(topics_list)

    for topic, f in fs.items():
        try:
            f.result()
            print("Topic {} successfully created".format(topic))
        except Exception as e:
            print("Failed to create topic {}: {}".format(topic, e))
    return topic_name


def get_topics_list():
    f = admin_client.list_topics().topics

    topics_list = list(admin_client.list_topics().topics.keys())
    print('LIST OF AVAILABLE TOPICS:')
    print(*topics_list, sep='\n')


def delivery_report(err, msg):
    if err is not None:
        print("Failed to deliver message: {}: {}".format(msg.value().decode('utf-8'), str(err)))
    else:
        print('Message with values {} successfully produced to Topic: {}, Partition: [{}] at offset {}'.format(
            msg.value().decode('utf-8'), msg.topic(), msg.partition(), msg.offset()))


def produce(data, topic_name):
    try:
        data_bytes = json.dumps(data).encode('utf-8')
        producer_client.produce(topic=topic_name, value=data_bytes, on_delivery=delivery_report)
        producer_client.flush()
    except KafkaException as e:
        print('Kafka failure ' + e)


def consume(topic_name):
    print('Consumer within group id', consumer_group_id, 'is subscribing topic', topic_name)
    consumer_client.subscribe([topic_name])
    messages_counter = 0
    messages_list = []

    while True:
        msg = consumer_client.poll(5.0)

        if msg is None:
            print("No message received from Kafka topic")
            break
        if msg.error():
            print("Consumer error: {}".format(msg.error()))
            break

        messages_counter += 1

        print(msg.value().decode('utf-8'))
        messages_list.append(msg.value().decode('utf-8'))

    print('Consumer received', messages_counter, 'messages')
    return messages_list


# ------------------------------------------------------------------------------------------

# creates topic with name 'events'
topic_name = create_topic()

# retrieves list of topics from broker
get_topics_list()

# loads the json file containing create order request
with open('createOrderRequest.json') as f:
    data = json.load(f)

# prints out the request
print(data)

# creates Kafka producer and produce data into the events topic
produce(data, topic_name)

# creates Kafka consumer and consume data from events topic
# docker exec -ti broker /usr/bin/kafka-console-consumer --bootstrap-server broker:29092 --from-beginning --topic events
# docker exec -ti broker /usr/bin/kafka-console-consumer --bootstrap-server localhost:9092 --from-beginning --topic events
messages_list = consume('events')
consumer_client.close()
print(messages_list)
