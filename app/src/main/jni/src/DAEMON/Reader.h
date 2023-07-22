//
// Created by Patel on 15-07-2023.
//

#ifndef COSMIC_ESP_PUBM_2_0_0_32BIT_READER_H
#define COSMIC_ESP_PUBM_2_0_0_32BIT_READER_H
template <typename T>
T Read(uintptr_t address) {
    T data;
    vm_readv(reinterpret_cast<void*>(address), reinterpret_cast<void*>(&data), sizeof(T));
    return data;
}

uintptr_t getMemoryAddr(uintptr_t address) {
    return Read<uintptr_t>(address);
}
uintptr_t getRealOffset(uintptr_t offset) {
    if (libbase == 0) {
        LOGE("Error: Can't Find Base Addr for Real Offset");
        return 0;
    }
    return (libbase + offset);
}
#endif //COSMIC_ESP_PUBM_2_0_0_32BIT_READER_H
