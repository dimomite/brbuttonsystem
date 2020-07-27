/* USER CODE BEGIN Header */
/**
  ******************************************************************************
  * @file           : main.h
  * @brief          : Header for main.c file.
  *                   This file contains the common defines of the application.
  ******************************************************************************
  * @attention
  *
  * <h2><center>&copy; Copyright (c) 2019 STMicroelectronics.
  * All rights reserved.</center></h2>
  *
  * This software component is licensed by ST under BSD 3-Clause license,
  * the "License"; You may not use this file except in compliance with the
  * License. You may obtain a copy of the License at:
  *                        opensource.org/licenses/BSD-3-Clause
  *
  ******************************************************************************
  */
/* USER CODE END Header */

/* Define to prevent recursive inclusion -------------------------------------*/
#ifndef __MAIN_H
#define __MAIN_H

#ifdef __cplusplus
extern "C" {
#endif

/* Includes ------------------------------------------------------------------*/
#include "stm32f1xx_hal.h"
#include "stm32f1xx_ll_i2c.h"
#include "stm32f1xx_ll_rcc.h"
#include "stm32f1xx_ll_bus.h"
#include "stm32f1xx_ll_system.h"
#include "stm32f1xx_ll_exti.h"
#include "stm32f1xx_ll_cortex.h"
#include "stm32f1xx_ll_utils.h"
#include "stm32f1xx_ll_pwr.h"
#include "stm32f1xx_ll_dma.h"
#include "stm32f1xx_ll_spi.h"
#include "stm32f1xx_ll_tim.h"
#include "stm32f1xx_ll_usart.h"
#include "stm32f1xx.h"
#include "stm32f1xx_ll_gpio.h"

/* Private includes ----------------------------------------------------------*/
/* USER CODE BEGIN Includes */

/* USER CODE END Includes */

/* Exported types ------------------------------------------------------------*/
/* USER CODE BEGIN ET */

/* USER CODE END ET */

/* Exported constants --------------------------------------------------------*/
/* USER CODE BEGIN EC */

/* USER CODE END EC */

/* Exported macro ------------------------------------------------------------*/
/* USER CODE BEGIN EM */

/* USER CODE END EM */

/* Exported functions prototypes ---------------------------------------------*/
void Error_Handler(void);

/* USER CODE BEGIN EFP */

/* USER CODE END EFP */

/* Private defines -----------------------------------------------------------*/
#define TOUCH_ENABLE_Pin LL_GPIO_PIN_13
#define TOUCH_ENABLE_GPIO_Port GPIOC
#define LCD_CS_Pin LL_GPIO_PIN_14
#define LCD_CS_GPIO_Port GPIOC
#define BACK_LCD_CS_Pin LL_GPIO_PIN_15
#define BACK_LCD_CS_GPIO_Port GPIOC
#define BACK_LED_CS_Pin LL_GPIO_PIN_0
#define BACK_LED_CS_GPIO_Port GPIOA
#define LED_RESET_Pin LL_GPIO_PIN_1
#define LED_RESET_GPIO_Port GPIOA
#define BTRX_MCUTX_Pin LL_GPIO_PIN_2
#define BTRX_MCUTX_GPIO_Port GPIOA
#define BTTX_MCURX_Pin LL_GPIO_PIN_3
#define BTTX_MCURX_GPIO_Port GPIOA
#define LCD_DC_Pin LL_GPIO_PIN_4
#define LCD_DC_GPIO_Port GPIOA
#define LCD_LED_Pin LL_GPIO_PIN_0
#define LCD_LED_GPIO_Port GPIOB
#define BT_POWER_Pin LL_GPIO_PIN_1
#define BT_POWER_GPIO_Port GPIOB
#define BUT0_Pin LL_GPIO_PIN_12
#define BUT0_GPIO_Port GPIOB
#define BUT1_Pin LL_GPIO_PIN_13
#define BUT1_GPIO_Port GPIOB
#define BUT2_Pin LL_GPIO_PIN_14
#define BUT2_GPIO_Port GPIOB
#define BUT3_Pin LL_GPIO_PIN_15
#define BUT3_GPIO_Port GPIOB
#define SOUND_PWM_Pin LL_GPIO_PIN_8
#define SOUND_PWM_GPIO_Port GPIOA
#define START_LED_Pin LL_GPIO_PIN_9
#define START_LED_GPIO_Port GPIOA
#define EXT_RW_Pin LL_GPIO_PIN_10
#define EXT_RW_GPIO_Port GPIOA
#define USB_PULLUP_Pin LL_GPIO_PIN_15
#define USB_PULLUP_GPIO_Port GPIOA
#define EXT_WRITE_Pin LL_GPIO_PIN_6
#define EXT_WRITE_GPIO_Port GPIOB
#define EXT_READ_Pin LL_GPIO_PIN_7
#define EXT_READ_GPIO_Port GPIOB
/* USER CODE BEGIN Private defines */

/* USER CODE END Private defines */

#ifdef __cplusplus
}
#endif

#endif /* __MAIN_H */

/************************ (C) COPYRIGHT STMicroelectronics *****END OF FILE****/
