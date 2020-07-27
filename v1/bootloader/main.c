/*
 * main.c
 *
 *  Created on: 25 сент. 2019 г.
 *      Author: DiMomite
 */

#include <stdint.h>

#include <stm32f10x.h>

// bootloader takes 4K + 2K for settings
#define USER_PROGRAM (0x08001000)

typedef void (*funct_ptr)(void);

int main() {

    RCC->APB2ENR |= 1 << RCC_APB2ENR_IOPBEN;

    funct_ptr app = (funct_ptr)(volatile uint32_t *)(USER_PROGRAM + 0x4);
    SCB->VTOR = USER_PROGRAM;
    asm volatile("msr msp, %0"::"g"(*(volatile u32 *) USER_PROGRAM));
    app();

    return 0;
}

