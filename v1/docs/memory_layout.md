# Memory layout

Contoller memory is organized in 3 sections:
* [bootloader](#bootloader)
* [settings](#settings)
* [main app](#main-app)

## Bootloader
First block of ROM is dedicated to bootloader.
Starts first and checks whether setting sections is initialized and app memory content is correct.
If any of those fails bootloader notifies user with color LED signal and prevents main app execution.
In bootloader mode USB connection should be used to flash main app memory or change settings.
Bootloaded also executes main app update on request.

## Settings

Second block in ROM. Takes 2 pages (2048B).
Each page is splitted onto 2 sections by 512B each.
First section contains setting common for new STM32F103-powered systems and old AT90USB162-bases (it has 512B of EEPROM).
Second section is for STM32 specific data only.


## Main app<a name="main-app"/>

Main app located rigth after settings and invoked from bootloader if memory-check and settings-check are succeessfully passed.

