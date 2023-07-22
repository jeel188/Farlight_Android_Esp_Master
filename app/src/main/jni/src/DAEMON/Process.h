//
// Created by Sagar Shah on 07/5/2021.
//

#ifndef COSMIC_PROCESS_H
#define COSMIC_PROCESS_H

#include <sys/types.h>
#include <sys/uio.h>
#include <sys/syscall.h>
#include <unistd.h>




#if defined(__arm__)
int process_vm_readv_syscall = 376;
int process_vm_writev_syscall = 377;
#elif defined(__aarch64__)
int process_vm_readv_syscall = 270;
int process_vm_writev_syscall = 271;
#elif defined(__i386__)
int process_vm_readv_syscall = 347;
int process_vm_writev_syscall = 348;
#else
int process_vm_readv_syscall = 310;
int process_vm_writev_syscall = 311;
#endif
ssize_t process_v(pid_t __pid,   struct iovec* __local_iov, unsigned long __local_iov_count, struct iovec* __remote_iov, unsigned long __remote_iov_count, unsigned long __flags) {
    return syscall(process_vm_readv_syscall, __pid, __local_iov, __local_iov_count, __remote_iov, __remote_iov_count, __flags);
}
int pvm(uintptr_t address, void* buffer,int size) {
    struct iovec local[1];
    struct iovec remote[1];

    local[0].iov_base = (void*)buffer;
    local[0].iov_len = size;
    remote[0].iov_base = (void*)address;
    remote[0].iov_len = size;
    ssize_t bytes = process_v(target_pid, local, 1, remote, 1, 0);
    return bytes == size;
}
bool pvm(void* address, void* buffer, size_t size, bool iswrite) {
    struct iovec local[1];
    struct iovec remote[1];
    local[0].iov_base = buffer;
    local[0].iov_len = size;
    remote[0].iov_base = address;
    remote[0].iov_len = size;
    if (target_pid < 0) {
        return false;
    }
    ssize_t bytes = syscall((iswrite ? process_vm_writev_syscall : process_vm_readv_syscall), target_pid, local, 1, remote, 1, 0);
    return bytes == size;
}

bool vm_readv(void* address, void* buffer, size_t size) {
    return pvm(address, buffer, size, false);
}

bool vm_writev(void* address, void* buffer, size_t size) {
    return pvm(address, buffer, size, true);
}
uint32_t MAX_SIZE = 100;
bool isDecrypt = false;
bool ProcessVirtualMemory(void *address, void *buffer, size_t size, bool iswrite) {
    struct iovec local[1];
    struct iovec remote[1];

    local[0].iov_base = buffer;
    local[0].iov_len = size;
    remote[0].iov_base = address;
    remote[0].iov_len = size;

    if (target_pid < 0) {
        return false;
    }

    ssize_t bytes = syscall((iswrite ? process_vm_writev_syscall : process_vm_readv_syscall),
                            target_pid, local, 1, remote, 1, 0);
    return bytes == size;
}
bool PVM_Read(void *address, void *buffer, size_t size) {
    return ProcessVirtualMemory(address, buffer, size, false);
}
std::string ReadString2(uintptr_t address, unsigned int size) {
    std::string name(size, '\0');
    PVM_Read((void *) address, (void *) name.data(), size * sizeof(char));
    name.shrink_to_fit();
    return name;
}
std::string XorCypher(std::string Input) {
    int key = Input.size();
    std::string Output = Input;

    for (int i = 0; i < Input.size(); i++)
        Output[i] = Input[i] ^ key;

    return Output;
}
template<typename T>
T *ReadArray(uintptr_t address, unsigned int size) {
    T data[size];
    T *ptr = data;
    PVM_Read(reinterpret_cast<void *>(address), reinterpret_cast<void *>(ptr), (sizeof(T) * size));
    return ptr;
}
#endif //COSMIC_PROCESS_H
