/**
 * File collects all static macros to access device hardware.
 * Should help with writing tests when this file can be replaced with mocks.
 */

#pragma once

#include <stm32f1xx_ll_tim.h>

#define readMainTimerValue() LL_TIM_GetCounter(TIM3)


