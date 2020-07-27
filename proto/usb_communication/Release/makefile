################################################################################
# Automatically-generated file. Do not edit!
################################################################################

-include ../makefile.init

RM := rm -rf

# All of the sources participating in the build are defined here
-include sources.mk
-include Startup/subdir.mk
-include Src/subdir.mk
-include Middlewares/ST/STM32_USB_Device_Library/Core/Src/subdir.mk
-include Middlewares/ST/STM32_USB_Device_Library/Class/CustomHID/Src/subdir.mk
-include Drivers/STM32F1xx_HAL_Driver/Src/subdir.mk
-include subdir.mk
-include objects.mk

ifneq ($(MAKECMDGOALS),clean)
ifneq ($(strip $(C_DEPS)),)
-include $(C_DEPS)
endif
endif

-include ../makefile.defs

# Add inputs and outputs from these tool invocations to the build variables 
EXECUTABLES += \
UsbHidCheck.elf \

SIZE_OUTPUT += \
default.size.stdout \

OBJDUMP_LIST += \
UsbHidCheck.list \

OBJCOPY_BIN += \
UsbHidCheck.bin \


# All Target
all:
	+@make --no-print-directory main-build && make --no-print-directory post-build

# Main-build Target
main-build: UsbHidCheck.elf secondary-outputs

# Tool invocations
UsbHidCheck.elf: $(OBJS) $(USER_OBJS) D:\projects\brbuttonsystem\proto\usb_communication\STM32F103C8TX_FLASH.ld
	arm-none-eabi-gcc -o "UsbHidCheck.elf" @"objects.list" $(USER_OBJS) $(LIBS) -mcpu=cortex-m3 -T"D:\projects\brbuttonsystem\proto\usb_communication\STM32F103C8TX_FLASH.ld" --specs=nosys.specs -Wl,-Map="UsbHidCheck.map" -Wl,--gc-sections -static --specs=nano.specs -mfloat-abi=soft -mthumb -Wl,--start-group -lc -lm -Wl,--end-group
	@echo 'Finished building target: $@'
	@echo ' '

default.size.stdout: $(EXECUTABLES)
	arm-none-eabi-size  $(EXECUTABLES)
	@echo 'Finished building: $@'
	@echo ' '

UsbHidCheck.list: $(EXECUTABLES)
	arm-none-eabi-objdump -h -S $(EXECUTABLES) > "UsbHidCheck.list"
	@echo 'Finished building: $@'
	@echo ' '

UsbHidCheck.bin: $(EXECUTABLES)
	arm-none-eabi-objcopy  -O binary $(EXECUTABLES) "UsbHidCheck.bin"
	@echo 'Finished building: $@'
	@echo ' '

# Other Targets
clean:
	-$(RM) *
	-@echo ' '

post-build:
	
	arm-none-eabi-objcopy -O ihex "UsbHidCheck.elf" "UsbHidCheck.hex"
	-@echo ' '

secondary-outputs: $(SIZE_OUTPUT) $(OBJDUMP_LIST) $(OBJCOPY_BIN)

.PHONY: all clean dependents
.SECONDARY: post-build

-include ../makefile.targets
