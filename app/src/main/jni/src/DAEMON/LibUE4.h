//
// Created by Patel on 15-07-2023.
//

#ifndef COSMIC_ESP_PUBM_2_0_0_32BIT_LIBUE4_H
#define COSMIC_ESP_PUBM_2_0_0_32BIT_LIBUE4_H
#define LIB_NAME "libUE4.so"
static uintptr_t libbase = 0;
static pid_t target_pid = -1;

int find_pid(const char *process_name)
{
    int id;
    pid_t pid = -1;
    DIR *dir;
    FILE *fp;
    char filename[32];
    char cmdline[256];

    struct dirent *entry;
    if (process_name == NULL)
    {
        return -1;
    }
    dir = opendir("/proc");
    if (dir == NULL)
    {
        return -1;
    }
    while ((entry = readdir(dir)) != NULL)
    {
        id = atoi(entry->d_name);
        if (id != 0)
        {
            sprintf(filename, "/proc/%d/cmdline", id);
            fp = fopen(filename, "r");
            if (fp)
            {
                fgets(cmdline, sizeof(cmdline), fp);
                fclose(fp);
                if (strcmp(process_name, cmdline) == 0)
                {
                    pid = id;
                    break;
                }
            }
        }
    }

    closedir(dir);
    return pid;
}
uintptr_t get_module_base(const char *module_name)
{
    FILE *fp;
    uintptr_t addr = 0;
    char filename[32], buffer[1024];
    snprintf(filename, sizeof(filename), "/proc/%d/maps", target_pid);
    fp = fopen(filename, "rt");
    if (fp != nullptr)
    {
        while (fgets(buffer, sizeof(buffer), fp))
        {
            if (strstr(buffer, module_name))
            {
#if defined(__LP64__)
                sscanf(buffer, "%lx-%*s", &addr);
#else
                sscanf(buffer, "%x-%*s", &addr);
#endif
                break;
            }
        }
        fclose(fp);
    }
    return addr;
}
static bool getModule()
{
    libbase = get_module_base(LIB_NAME);
    return libbase > 0 ? true : false;
}

#endif
