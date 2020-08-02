/**
 * File collects all static macros to access device hardware.
 * Should help with writing tests when this file can be replaced with mocks.
 */

#pragma once

#include <stm32f1xx_ll_tim.h>
#include <main.h>

#define readMainTimerValue() LL_TIM_GetCounter(TIM3)

#define debugLedOn() LL_GPIO_SetOutputPin(LCD_RESET_GPIO_Port, LCD_RESET_Pin)
#define debugLedOff() LL_GPIO_ResetOutputPin(LCD_RESET_GPIO_Port, LCD_RESET_Pin)


