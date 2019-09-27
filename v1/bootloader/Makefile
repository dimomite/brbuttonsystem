BASE_TARGET = HIDBOOTLOADER

BUILD_DIR = build
OUTPUT_DIR = out

DEVICE = STM32F10X_MD

TARGET = $(addprefix $(OUTPUT_DIR)/,$(BASE_TARGET))

VECTOR_TABLE_OFFSET = 0x0000

#include ./src/Makefile
SRCS += main.c

DEVICE_SOURCES += ../STM32F10x_StdPeriph_Lib_V3.5.0/Libraries/CMSIS/CM3/DeviceSupport/ST/STM32F10x/startup/arm/startup_stm32f10x_md.s
DEVICE_SOURCES += ../STM32F10x_StdPeriph_Lib_V3.5.0/Libraries/CMSIS/CM3/DeviceSupport/ST/STM32F10x/system_stm32f10x.c

INCLUDE_DIRS += -I .
INCLUDE_DIRS += -I ../STM32F10x_StdPeriph_Lib_V3.5.0/Libraries/CMSIS/CM3/CoreSupport
INCLUDE_DIRS += -I ../STM32F10x_StdPeriph_Lib_V3.5.0/Libraries/CMSIS/CM3/DeviceSupport/ST/STM32F10x

LINKER_SCRIPT = STM32F103C8T6.ld

SRCS += $(subst src,,$(DEVICE_SRCS))
OBJS = $(addprefix $(BUILD_DIR)/,$(addsuffix .o,$(basename $(SRCS))))

ASFLAGS += -mcpu=cortex-m3 -mthumb

CFLAGS += -mcpu=cortex-m3 -mthumb -Wall -Os
CFLAGS += -fno-common -static
CFLAGS += -ffunction-sections -fdata-sections
CFLAGS += -specs=nano.specs -specs=nosys.specs
#CFLAGS += -fltos
CFLAGS += $(INCLUDE_DIRS)
CFLAGS += -D$(DEVICE)
CFLAGS += -DVECTOR_TABLE_OFFSET=$(VECTOR_TABLE_OFFSET)

LDFLAGS += -Wl,-Map=$(TARGET).map,--cref
LDFLAGS += -Wl,--gc-sections
LDFLAGS += -Wl,--print-memory-usage
#LDFLAGS += -flto
LDFLAGS += -T$(LINKER_SCRIPT)

CC = arm-none-eabi-gcc
AS = arm-none-eabi-as
LD = arm-none-eabi-ld
OBJDUMP = arm-none-eabi-objdump
OBJCOPY = arm-none-eabi-objcopy
SIZE = arm-none-eabi-size
GDB = arm-none-eabi-gdb

ECHO = echo
RM = rm

.PHONY: all build output info size clean flash directories

all: $(SRCS) gccversion directories build output info size

directories:
$(OUTPUT_DIR) $(BUILD_DIR):
	mkdir -p $@

build: $(TARGET).elf

$(TARGET).elf: $(OBJS)
	$(CC) $(LDFLAGS) $(CFLAGS) $(OBJS) -o "$@"

.SECONDARY: $(OBJS)

$(BUILD_DIR)/%.o: %.c
	$(CC) $(CFLAGS) -c "$<" -o "$@"

$(BUILD_DIR)/%.o: %.s
	$(AS) $(ASFLAGS) -c "$<" -o "$@"

output: $(TARGET).hex $(TARGET).bin

$(TARGET).hex: $(TARGET).elf
	$(OBJCOPY) -O ihex $< $@

$(TARGET).bin: $(TARGET).elf
	$(OBJCOPY) -O binary $< $@
	
gccversion:
	@$(CC) --version

info: $(TARGET).elf
	$(OBJDUMP) -x -S $(TARGET).elf > $(TARGET).lst
	$(OBJDUMP) -D $(TARGET).elf > $(TARGET).dis
	$(SIZE) $(TARGET).elf > $(TARGET).size

size: $(TARGET).elf
	$(SIZE) $(TARGET).elf

clean:
	$(RM) -f $(TARGET).elf
	$(RM) -f $(TARGET).bin
	$(RM) -f $(TARGET).hex
	$(RM) -f $(TARGET).size
	$(RM) -f $(TARGET).lst
	$(RM) -f $(TARGET).dis
	$(RM) -f $(TARGET).map
	$(RM) -f $(OBJS)
	