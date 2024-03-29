/* USER CODE BEGIN Header */
/**
  ******************************************************************************
  * @file           : usbd_custom_hid_if.c
  * @version        : v2.0_Cube
  * @brief          : USB Device Custom HID interface file.
  ******************************************************************************
  * @attention
  *
  * <h2><center>&copy; Copyright (c) 2019 STMicroelectronics.
  * All rights reserved.</center></h2>
  *
  * This software component is licensed by ST under Ultimate Liberty license
  * SLA0044, the "License"; You may not use this file except in compliance with
  * the License. You may obtain a copy of the License at:
  *                             www.st.com/SLA0044
  *
  ******************************************************************************
  */
/* USER CODE END Header */

/* Includes ------------------------------------------------------------------*/
#include "usbd_custom_hid_if.h"

/* USER CODE BEGIN INCLUDE */
#include "playersledsctrl.h"
#include "lcdctrl.h"
#include "entertainmentctrl.h"
#include "btctrl.h"
/* USER CODE END INCLUDE */

/* Private typedef -----------------------------------------------------------*/
/* Private define ------------------------------------------------------------*/
/* Private macro -------------------------------------------------------------*/

/* USER CODE BEGIN PV */
/* Private variables ---------------------------------------------------------*/
extern PlayerLedsCtrl playerLedsCtrl;
extern LcdCtrl mainLcdCtrl;
extern EntertainmentCtrl entCtrl;
extern BtCtrl btCtrl;
/* USER CODE END PV */

/** @addtogroup STM32_USB_OTG_DEVICE_LIBRARY
  * @brief Usb device.
  * @{
  */

/** @addtogroup USBD_CUSTOM_HID
  * @{
  */

/** @defgroup USBD_CUSTOM_HID_Private_TypesDefinitions USBD_CUSTOM_HID_Private_TypesDefinitions
  * @brief Private types.
  * @{
  */

/* USER CODE BEGIN PRIVATE_TYPES */

/* USER CODE END PRIVATE_TYPES */

/**
  * @}
  */

/** @defgroup USBD_CUSTOM_HID_Private_Defines USBD_CUSTOM_HID_Private_Defines
  * @brief Private defines.
  * @{
  */

/* USER CODE BEGIN PRIVATE_DEFINES */

/* USER CODE END PRIVATE_DEFINES */

/**
  * @}
  */

/** @defgroup USBD_CUSTOM_HID_Private_Macros USBD_CUSTOM_HID_Private_Macros
  * @brief Private macros.
  * @{
  */

/* USER CODE BEGIN PRIVATE_MACRO */

/* USER CODE END PRIVATE_MACRO */

/**
  * @}
  */

/** @defgroup USBD_CUSTOM_HID_Private_Variables USBD_CUSTOM_HID_Private_Variables
  * @brief Private variables.
  * @{
  */

/** Usb HID report descriptor. */
__ALIGN_BEGIN static uint8_t CUSTOM_HID_ReportDesc_FS[USBD_CUSTOM_HID_REPORT_DESC_SIZE] __ALIGN_END =
{
  /* USER CODE BEGIN 0 */
	    0x06, 0x00, 0xff,              // 	USAGE_PAGE (Generic Desktop)
	    0x09, 0x01,                    // 	USAGE (Vendor Usage 1)
	    // System Parameters
	    0xa1, 0x01,                    // 	COLLECTION (Application)

	    0x85, 0x01,                    //   REPORT_ID (1)
	    0x09, 0x01,                    //   USAGE (Vendor Usage 1)
	    0x75, 0x08,                    //   REPORT_SIZE (8)
	    0x95, USBD_CUSTOMHID_OUTREPORT_BUF_SIZE, // REPORT_COUNT (4)
	    0xb1, 0x82,                    //   FEATURE (Data,Var,Abs,Vol)
	    0x85, 0x01,                    //   REPORT_ID (1)
	    0x09, 0x01,                    //   USAGE (Vendor Usage 1)
	    0x91, 0x82,                    //   OUTPUT (Data,Var,Abs,Vol)

	    0x85, 0x02,                    //   REPORT_ID (4)
	    0x09, 0x02,                    //   USAGE (Vendor Usage 4)
	    0x75, 0x08,                    //   REPORT_SIZE (8)
	    0x95, USBD_CUSTOMHID_OUTREPORT_BUF_SIZE, // REPORT_COUNT (4)
	    0x81, 0x82,                    //   INPUT (Data,Var,Abs,Vol)
  /* USER CODE END 0 */
  0xC0    /*     END_COLLECTION	             */
};

/* USER CODE BEGIN PRIVATE_VARIABLES */

//static uint8_t usbData[USBD_CUSTOMHID_OUTREPORT_BUF_SIZE];
static uint8_t dataToSend[USBD_CUSTOMHID_OUTREPORT_BUF_SIZE + 1] = {2, 0, 0, 0, 0};

/* USER CODE END PRIVATE_VARIABLES */

/**
  * @}
  */

/** @defgroup USBD_CUSTOM_HID_Exported_Variables USBD_CUSTOM_HID_Exported_Variables
  * @brief Public variables.
  * @{
  */
extern USBD_HandleTypeDef hUsbDeviceFS;

/* USER CODE BEGIN EXPORTED_VARIABLES */

/* USER CODE END EXPORTED_VARIABLES */
/**
  * @}
  */

/** @defgroup USBD_CUSTOM_HID_Private_FunctionPrototypes USBD_CUSTOM_HID_Private_FunctionPrototypes
  * @brief Private functions declaration.
  * @{
  */

static int8_t CUSTOM_HID_Init_FS(void);
static int8_t CUSTOM_HID_DeInit_FS(void);
static int8_t CUSTOM_HID_OutEvent_FS(uint8_t event_idx, uint8_t state);

/**
  * @}
  */

USBD_CUSTOM_HID_ItfTypeDef USBD_CustomHID_fops_FS =
{
  CUSTOM_HID_ReportDesc_FS,
  CUSTOM_HID_Init_FS,
  CUSTOM_HID_DeInit_FS,
  CUSTOM_HID_OutEvent_FS
};

/** @defgroup USBD_CUSTOM_HID_Private_Functions USBD_CUSTOM_HID_Private_Functions
  * @brief Private functions.
  * @{
  */

/* Private functions ---------------------------------------------------------*/

/**
  * @brief  Initializes the CUSTOM HID media low layer
  * @retval USBD_OK if all operations are OK else USBD_FAIL
  */
static int8_t CUSTOM_HID_Init_FS(void)
{
  /* USER CODE BEGIN 4 */
  return (USBD_OK);
  /* USER CODE END 4 */
}

/**
  * @brief  DeInitializes the CUSTOM HID media low layer
  * @retval USBD_OK if all operations are OK else USBD_FAIL
  */
static int8_t CUSTOM_HID_DeInit_FS(void)
{
  /* USER CODE BEGIN 5 */
  return (USBD_OK);
  /* USER CODE END 5 */
}

/**
  * @brief  Manage the CUSTOM HID class events
  * @param  event_idx: Event index
  * @param  state: Event state
  * @retval USBD_OK if all operations are OK else USBD_FAIL
  */
static int8_t CUSTOM_HID_OutEvent_FS(uint8_t event_idx, uint8_t state)
{
  /* USER CODE BEGIN 6 */
  USBD_CUSTOM_HID_HandleTypeDef *hhid = (USBD_CUSTOM_HID_HandleTypeDef*)hUsbDeviceFS.pClassData;
//  memcpy(usbData, hhid->Report_buf, USBD_CUSTOMHID_OUTREPORT_BUF_SIZE);
  switch (hhid->Report_buf[0]) {
  case 2: HAL_GPIO_WritePin(BlueLed_GPIO_Port, BlueLed_Pin, GPIO_PIN_SET); break;
  case 5: HAL_GPIO_WritePin(BlueLed_GPIO_Port, BlueLed_Pin, GPIO_PIN_RESET); break;
  default: break;
  }

  return (USBD_OK);
  /* USER CODE END 6 */
}

/* USER CODE BEGIN 7 */
/**
  * @brief  Send the report to the Host
  * @param  report: The report to be sent
  * @param  len: The report length
  * @retval USBD_OK if all operations are OK else USBD_FAIL
  */
/*
static int8_t USBD_CUSTOM_HID_SendReport_FS(uint8_t *report, uint16_t len)
{
  return USBD_CUSTOM_HID_SendReport(&hUsbDeviceFS, report, len);
}
*/
/* USER CODE END 7 */

/* USER CODE BEGIN PRIVATE_FUNCTIONS_IMPLEMENTATION */

void HAL_GPIO_EXTI_Callback(uint16_t GPIO_Pin);

static void handleButtonChange(uint8_t isPressed) {
	HAL_GPIO_WritePin(BlueLed_GPIO_Port, BlueLed_Pin, isPressed ? GPIO_PIN_RESET : GPIO_PIN_SET);

	dataToSend[1] = isPressed;
	USBD_CUSTOM_HID_SendReport(&hUsbDeviceFS, dataToSend, sizeof(dataToSend));
}

#define ACTIVE_BUTTON_PIN (GPIO_PIN_SET)

void HAL_GPIO_EXTI_Callback(uint16_t GPIO_Pin) {
	if (ACTIVE_BUTTON_PIN == HAL_GPIO_ReadPin(Button0_GPIO_Port, Button0_Pin)) {
		handleButtonChange(10);
		PlayerLeds_SetPlayer(&playerLedsCtrl, PLAYER_1, PLAYER_VIS_ONLY_SYSTEM);
		LCD_DrawFilledRectangle(&mainLcdCtrl, 65, 170, 40, 60, LCD_COLOR_RED);
//		ENT_SoundOn(&entCtrl, ENT_SOUND_START);
//		ENT_StartLedOn(&entCtrl, 200);
		BT_SendByte(&btCtrl, 1);
	} else if (ACTIVE_BUTTON_PIN == HAL_GPIO_ReadPin(Button1_GPIO_Port, Button1_Pin)) {
		handleButtonChange(20);
		PlayerLeds_SetPlayer(&playerLedsCtrl, PLAYER_2, PLAYER_VIS_ONLY_SYSTEM);
		LCD_DrawFilledRectangle(&mainLcdCtrl, 115, 170, 40, 60, LCD_COLOR_GREEN);
//		ENT_SoundOn(&entCtrl, ENT_SOUND_PRESSED);
//		ENT_StartLedOn(&entCtrl, 400);
		BT_SendByte(&btCtrl, 2);
	} else if (ACTIVE_BUTTON_PIN == HAL_GPIO_ReadPin(Button2_GPIO_Port, Button2_Pin)) {
		handleButtonChange(30);
		PlayerLeds_SetPlayer(&playerLedsCtrl, PLAYER_3, PLAYER_VIS_ONLY_SYSTEM);
		LCD_DrawFilledRectangle(&mainLcdCtrl, 165, 170, 40, 60, LCD_COLOR_YELLOW);
//		ENT_SoundOn(&entCtrl, ENT_SOUND_FALSE_START);
//		ENT_StartLedOn(&entCtrl, 600);
		BT_SendByte(&btCtrl, 3);
	} else if (ACTIVE_BUTTON_PIN == HAL_GPIO_ReadPin(Button3_GPIO_Port, Button3_Pin)) {
		handleButtonChange(40);
		PlayerLeds_SetPlayer(&playerLedsCtrl, PLAYER_4, PLAYER_VIS_ONLY_SYSTEM);
		LCD_DrawFilledRectangle(&mainLcdCtrl, 215, 170, 40, 60, LCD_COLOR_BLUE);
//		ENT_StartLedOn(&entCtrl, 800);
		BT_SendByte(&btCtrl, 4);
	} else {
		handleButtonChange(0);
		BT_SendByte(&btCtrl, 0);
//		ENT_StartLedOff(&entCtrl);
//		ENT_SoundOff(&entCtrl);
		PlayerLeds_ClearAll(&playerLedsCtrl);
		LCD_DrawFilledRectangle(&mainLcdCtrl, 65, 170, 190, 60, LCD_COLOR_WHITE);
	}
}

/* USER CODE END PRIVATE_FUNCTIONS_IMPLEMENTATION */
/**
  * @}
  */

/**
  * @}
  */

/**
  * @}
  */

/************************ (C) COPYRIGHT STMicroelectronics *****END OF FILE****/

