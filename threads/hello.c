#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define BUFFER_SIZE 5
int buffer[BUFFER_SIZE];
int count = 0;

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t notFull = PTHREAD_COND_INITIALIZER;
pthread_cond_t notEmpty = PTHREAD_COND_INITIALIZER;

void *producer(void *arg)
{
    int id = *((int *)arg);
    int item;

    for (int i = 0; i < 5; i++)
    {
        item = rand() % 100; // Produce a random item
        pthread_mutex_lock(&mutex);

        while (count == BUFFER_SIZE)
        {
            pthread_cond_wait(&notFull, &mutex); // Wait for space in buffer
        }

        buffer[count] = item;
        count++;
        printf("Producer %d produced item %d\n", id, item);

        pthread_cond_signal(&notEmpty); // Signal that buffer has new items
        pthread_mutex_unlock(&mutex);
    }

    return NULL;
}

void *consumer(void *arg)
{
    int id = *((int *)arg);
    int item;

    for (int i = 0; i < 5; i++)
    {
        pthread_mutex_lock(&mutex);

        while (count == 0)
        {
            pthread_cond_wait(&notEmpty, &mutex); // Wait for items in buffer
        }

        item = buffer[count - 1];
        count--;
        printf("Consumer %d consumed item %d\n", id, item);

        pthread_cond_signal(&notFull); // Signal that buffer has empty space
        pthread_mutex_unlock(&mutex);
    }

    return NULL;
}

int main()
{
    int numProducers, numConsumers;

    printf("Enter the number of producers: ");
    scanf("%d", &numProducers);
    printf("Enter the number of consumers: ");
    scanf("%d", &numConsumers);

    pthread_t producers[numProducers], consumers[numConsumers];
    int producerIds[numProducers], consumerIds[numConsumers];

    for (int i = 0; i < numProducers; i++)
    {
        producerIds[i] = i + 1;
        pthread_create(&producers[i], NULL, producer, &producerIds[i]);
    }

    for (int i = 0; i < numConsumers; i++)
    {
        consumerIds[i] = i + 1;
        pthread_create(&consumers[i], NULL, consumer, &consumerIds[i]);
    }

    for (int i = 0; i < numProducers; i++)
    {
        pthread_join(producers[i], NULL);
    }

    for (int i = 0; i < numConsumers; i++)
    {
        pthread_join(consumers[i], NULL);
    }

    pthread_mutex_destroy(&mutex);
    pthread_cond_destroy(&notFull);
    pthread_cond_destroy(&notEmpty);

    return 0;
}
