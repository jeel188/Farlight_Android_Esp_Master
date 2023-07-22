

#ifndef COSMIC_SOCKET_H
#define COSMIC_SOCKET_H

#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <string>
#include <vector>
#include <errno.h>
#include <stdio.h>
#include <sys/un.h>

#define SOCKET_NAME "JeelPatel"
#define BACKLOG 8
#define PacketSize 4096

int listenfd, acceptfd;
bool isCreated;
sockaddr_un addr_server;
char socket_name[108];
bool Create();
bool Bind();
bool Listen();
bool Accept();
void Close();
int sendData(void *inData, size_t size);
int send(void* inData, size_t size);
int recvData(void *outData, size_t size);
size_t receive(void* outData);

bool Create() {
    isCreated = (listenfd = socket(AF_UNIX, SOCK_STREAM | SOCK_CLOEXEC, 0)) >= 0;
    return isCreated;
}

bool Bind() {
    memset(socket_name, 0, sizeof(socket_name));
    memcpy(&socket_name[0], "\0", 1);
    strcpy(&socket_name[1], SOCKET_NAME);

    memset(&addr_server, 0, sizeof(addr_server));
    addr_server.sun_family = AF_UNIX; // Unix Domain instead of AF_INET IP domain
    strncpy(addr_server.sun_path, socket_name, sizeof(addr_server.sun_path) - 1); // 108 char max

    if (bind(listenfd, (sockaddr *) &addr_server, sizeof(addr_server)) < 0) {
        Close();
        return false;
    }
    return true;
}

bool Listen() {
    if (listen(listenfd, BACKLOG) < 0) {
        Close();
        return false;
    }
    return true;
}

bool Accept() {
    if ((acceptfd = accept(listenfd, nullptr, nullptr)) < 0) {
        Close();
        return false;
    }
    return true;
}

void Close() {
    if (acceptfd > 0)
        close(acceptfd);
    if (listenfd > 0)
        close(listenfd);
}

int sendData(void *inData, size_t size) {
    char *buffer = (char *) inData;
    int numSent = 0;

    while (size) {
        do {
            numSent = write(acceptfd, buffer, size);
        } while (numSent == -1 && EINTR == errno);

        if (numSent <= 0) {
            Close();
            break;
        }

        size -= numSent;
        buffer += numSent;
    }
    return numSent;
}

int send(void* inData, size_t size) {
    uint32_t length = htonl(size);
    if(sendData(&length, sizeof(uint32_t)) <= 0){
        return 0;
    }
    return sendData(inData, size) > 0;
}

int recvData(void *outData, size_t size) {
    char *buffer = (char *) outData;
    int numReceived = 0;

    while (size) {
        do {
            numReceived = read(acceptfd, buffer, size);
        } while (numReceived == -1 && EINTR == errno);

        if (numReceived <= 0) {
            Close();
            break;
        }

        size -= numReceived;
        buffer += numReceived;
    }
    return numReceived;
}

size_t receive(void *outData) {
    uint32_t length = 0;
    int code = recvData(&length, sizeof(uint32_t));
    if(code > 0){
        length = ntohl(length);
        recvData(outData, static_cast<size_t>(length));
    }
    return length;
}


#endif //COSMIC_SOCKET_H
