/*
 * lcdctrl.c
 *
 *  Created on: Aug 15, 2019
 *      Author: DiMomite
 */

#include "main.h"
#include "lcdctrl.h"
#include "ili9341commands.h"

#define DISPLAY_WIDTH   (320)
#define DISPLAY_HEIGHT  (240)
#define DISPLAY_USE_BGR

#ifdef DISPLAY_USE_BGR
#define DISPLAY_COLOR_SPACE (0x08)
#else
#define DISPLAY_COLOR_SPACE (0x00)
#endif // #ifdef DISPLAY_USE_BGR

#define selectDisplay() HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_RESET)
#define unselectDisplay() HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_SET)

#define switchToCommands() HAL_GPIO_WritePin(LCD_DC_GPIO_Port, LCD_DC_Pin, GPIO_PIN_RESET)
#define switchToData() HAL_GPIO_WritePin(LCD_DC_GPIO_Port, LCD_DC_Pin, GPIO_PIN_SET)

extern SPI_HandleTypeDef hspi1;

static void sendInitSequence(LcdCtrl* lcdctrl);

static void sendCmd(uint8_t cmd) {
    switchToCommands();
    selectDisplay();
//    LL_SPI_TransmitData8(SPI1, cmd);
    HAL_SPI_Transmit(&hspi1, &cmd, 1, 1);
    unselectDisplay();
}

static void sendData(uint8_t data) {
    switchToData();
    selectDisplay();
//    LL_SPI_TransmitData8(SPI1, data);
    HAL_SPI_Transmit(&hspi1, &data, 1, 1);
    unselectDisplay();
}

void LCD_Init(LcdCtrl* lcdctrl) {
    if (!lcdctrl) return;

    HAL_GPIO_WritePin(LCD_LED_GPIO_Port, LCD_LED_Pin, GPIO_PIN_RESET);
    HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_RESET);
    HAL_GPIO_WritePin(LCD_RESET_GPIO_Port, LCD_RESET_Pin, GPIO_PIN_SET);

    sendInitSequence(lcdctrl);

    LCD_ChangeRotation(lcdctrl, LCD_ORIENTATION_DEFAULT);
}

void LCD_ChangeRotation(LcdCtrl* lcdctrl, LcdOrientation orientation) {
    if (!lcdctrl) return;
    if (LCD_ORIENTATION_UNDEFINED == orientation) return;

    uint8_t command = DISPLAY_COLOR_SPACE;

    lcdctrl->orientation = orientation;
    switch (orientation) {
    case LCD_ORIENTATION_VERTICAL:
        lcdctrl->width = DISPLAY_HEIGHT;
        lcdctrl->height = DISPLAY_WIDTH;
        command |= 0x40; // TODO check this orientation case
        break;
    case LCD_ORIENTATION_HORIZONTAL:
        lcdctrl->width = DISPLAY_WIDTH;
        lcdctrl->height = DISPLAY_HEIGHT;
        command |= 0x20; // TODO check this orientation case
        break;
    case LCD_ORIENTATION_VERTICAL_INVERTER:
        lcdctrl->width = DISPLAY_HEIGHT;
        lcdctrl->height = DISPLAY_WIDTH;
        command |= 0x80; // TODO check this orientation case
        break;
    case LCD_ORIENTATION_HORIZONTAL_INVERTER:
        lcdctrl->width = DISPLAY_WIDTH;
        lcdctrl->height = DISPLAY_HEIGHT;
        command |= 0xE0; // TODO check this orientation case
        break;
    case LCD_ORIENTATION_UNDEFINED: break; // compiler leaves warning here despite initial check
    }

    sendCmd(ILI9341_CMD_Memory_Access_Control);
    sendData(command);

}

void LCD_SetArea(LcdCtrl* lcdctrl, uint16_t x1, uint16_t y1, uint16_t x2, uint16_t y2) {
    if (!lcdctrl) return;

    if (x1 < 0) x1 = 0;
    if (x2 >= lcdctrl->width) x2 = lcdctrl->width - 1;
    if (y1 < 0) y1 = 0;
    if (y2 >= lcdctrl->height) y2 = lcdctrl->height - 1;

    sendCmd(ILI9341_CMD_Column_Address_Set);
    sendData(x1 >> 8);
    sendData(x1);
    sendData(x2 >> 8);
    sendData(x2);

    sendCmd(ILI9341_CMD_Page_Address_Set);
    sendData(y1 >> 8);
    sendData(y1);
    sendData(y2 >> 8);
    sendData(y2);
}

void LCD_FillWithColor(LcdCtrl* lcdctrl, uint16_t color, uint32_t pixelsCount) {
    if (!lcdctrl) return;
    {
        const uint32_t maxSize = lcdctrl->width * lcdctrl->height;
        if (pixelsCount > maxSize) pixelsCount = maxSize;
    }

    sendCmd(ILI9341_CMD_Memory_Write);

    HAL_GPIO_WritePin(LCD_DC_GPIO_Port, LCD_DC_Pin, GPIO_PIN_SET);
    HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_RESET);

    uint8_t c1 = (uint8_t) (color >> 8);
    uint8_t c2 = (uint8_t) color;

    for (uint32_t i = 0; i < pixelsCount; ++i) {
//        LL_SPI_TransmitData8(SPI1, c1);
//        LL_SPI_TransmitData8(SPI1, c2);
        HAL_SPI_Transmit(&hspi1, &c1, 1, 1);
        HAL_SPI_Transmit(&hspi1, &c2, 1, 1);
    }

    HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_SET);
}

void LCD_DrawFilledRectangle(LcdCtrl* lcdctrl, uint16_t x1, uint16_t y1, uint16_t x2, uint16_t y2, uint16_t color) {
    if (!lcdctrl) return;

    uint32_t size = (x2 - x1) * (y2 - y1);
    if (size < 0) size = -size;

    LCD_SetArea(lcdctrl, x1, y1, x2, y2);
    LCD_FillWithColor(lcdctrl, color, size);
}

void LCD_DrawPixel(LcdCtrl* lcdctrl, uint16_t x, uint16_t y, uint16_t color) {
    if (!lcdctrl) return;
    if (x >= lcdctrl->width || y >= lcdctrl->height) return;

    LCD_DrawFilledRectangle(lcdctrl, x, y, x + 1, y + 1, color);

//    uint8_t data;
//
//    //ADDRESS
//    HAL_GPIO_WritePin(LCD_DC_GPIO_Port, LCD_DC_Pin, GPIO_PIN_RESET);
//    HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_RESET);
//    data = 0x2A;
//    HAL_SPI_Transmit(&hspi1, &data, 1, 1);
//    HAL_GPIO_WritePin(LCD_DC_GPIO_Port, LCD_DC_Pin, GPIO_PIN_SET);
//    HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_SET);
//
//    //XDATA
//    HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_RESET);
//    unsigned char Temp_Buffer[4] = { x >> 8, x, (x + 1) >> 8, (x + 1) };
//    HAL_SPI_Transmit(&hspi1, Temp_Buffer, 4, 1);
//    HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_SET);
//
//    //ADDRESS
//    HAL_GPIO_WritePin(LCD_DC_GPIO_Port, LCD_DC_Pin, GPIO_PIN_RESET);
//    HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_RESET);
//    data = 0x2B;
//    HAL_SPI_Transmit(&hspi1, &data, 1, 1);
//    HAL_GPIO_WritePin(LCD_DC_GPIO_Port, LCD_DC_Pin, GPIO_PIN_SET);
//    HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_SET);
//
//    //YDATA
//    HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_RESET);
//    unsigned char Temp_Buffer1[4] = { y >> 8, y, (y + 1) >> 8, (y + 1) };
//    HAL_SPI_Transmit(&hspi1, Temp_Buffer1, 4, 1);
//    HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_SET);
//
//    //ADDRESS
//    HAL_GPIO_WritePin(LCD_DC_GPIO_Port, LCD_DC_Pin, GPIO_PIN_RESET);
//    HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_RESET);
//    data = 0x2C;
//    HAL_SPI_Transmit(&hspi1, &data, 1, 1);
//    HAL_GPIO_WritePin(LCD_DC_GPIO_Port, LCD_DC_Pin, GPIO_PIN_SET);
//    HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_SET);
//
//    //COLOUR
//    HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_RESET);
//    unsigned char Temp_Buffer2[2] = { color >> 8, color };
//    HAL_SPI_Transmit(&hspi1, Temp_Buffer2, 2, 1);
//    HAL_GPIO_WritePin(LCD_CS_GPIO_Port, LCD_CS_Pin, GPIO_PIN_SET);
}

void LCD_ChangeInversionMode(LcdCtrl* lcdctrl, const uint8_t isEnable) {
    if (!lcdctrl) return;

    if (isEnable) {
        sendCmd(ILI9341_CMD_Display_Inversion_ON);
    } else {
        sendCmd(ILI9341_CMD_Display_Inversion_OFF);
    }
}

void LCD_ReadId(LcdCtrl* lcdctrl, uint32_t* id) {
    if (!lcdctrl) return;


}

static void sendInitSequence(LcdCtrl* lcdctrl) {
    sendCmd(ILI9341_CMD_Software_Reset);
    HAL_Delay(1000); // TODO check whether this one is really needed // TODO use device abstraction "singleDelay()" call // TODO represent display init as FSM

    sendCmd(0xef); // undocumented
    sendData(0x03);
    sendData(0x80);
    sendData(0x02);

    sendCmd(0xca); // undocumented
    sendData(0xc3);
    sendData(0x08);
    sendData(0x50);

    sendCmd(ILI9341_CMD_Power_Control_A);
    sendData(0x39);
    sendData(0x2C);
    sendData(0x00);
    sendData(0x34);
    sendData(0x02);

    sendCmd(ILI9341_CMD_Power_Control_B);
    sendData(0x00);
    sendData(0xc1); // default 0x81 - PCEQ: PC and EQ operation for power saving -  1:enable this function
    sendData(0x30);

    sendCmd(ILI9341_CMD_Driver_Timing_Control_A);
    sendData(0x85); // def 54
    sendData(0x00); // def 11
    sendData(0x78); // def 7a

    sendCmd(ILI9341_CMD_Driver_Timing_Control_B);
    sendData(0x00); // def 66
    sendData(0x00);

    sendCmd(ILI9341_CMD_Power_On_Sequence_Control);
    sendData(0x64); // def 55
    sendData(0x03); // def 01
    sendData(0x12); // def 23
    sendData(0x81); // def 1

    sendCmd(ILI9341_CMD_Pump_Ratio_Control);
    sendData(0x20); // def 10

    sendCmd(ILI9341_CMD_Power_Control_1);
    sendData(0x23); // def 21(4.5V) while 23 -> 4.6V

    sendCmd(ILI9341_CMD_Power_Control_2);
    sendData(0x10);

    sendCmd(ILI9341_CMD_VCOM_Control_1);
    sendData(0x3E); // def 31
    sendData(0x28); // def 3c

    sendCmd(ILI9341_CMD_VCOM_Control_2);
    sendData(0x86); // def c0, 86 -> VMH=58 VML=58

    // change from RGB to BGR is here
    sendCmd(ILI9341_CMD_Memory_Access_Control);
    sendData(0x40 | DISPLAY_COLOR_SPACE); // def 00, MX = 1, BGR = 1 !!! Not RGB for some reason !!!

    sendCmd(ILI9341_CMD_COLMOD_Pixel_Format_Set);
    sendData(0x55); // def 66(18 bits/pixel), while 55 -> 16 bits/pixel

    sendCmd(ILI9341_CMD_Frame_Rate_Control_In_Normal_Mode_Full_Colors);
    sendData(0x00);
    sendData(0x18); // def 1b(70Hz), while 18 -> 79

    sendCmd(ILI9341_CMD_Display_Function_Control);
    sendData(0x08); // def 0a
    sendData(0x82);
    sendData(0x27);
    // may need 4th data

    // TODO check with 0x02 - default value
    sendCmd(ILI9341_CMD_Enable_3G);
    sendData(0x00); // def 02, this change is very strange, for 02 3 gamma control is also disabled

    sendCmd(ILI9341_CMD_Gamma_Set);
    sendData(0x01);

    sendCmd(ILI9341_CMD_Positive_Gamma_Correction);
    sendData(0x0F); // def 08
    sendData(0x31); // no def
    sendData(0x2B); // no def
    sendData(0x0C); // def 05
    sendData(0x0E); // no def
    sendData(0x08); // def 09
    sendData(0x4E); // no def
    sendData(0xF1); // no def
    sendData(0x37); // no def
    sendData(0x07); // def 0b
    sendData(0x10); // no def
    sendData(0x03); // def 00
    sendData(0x0E); // no def
    sendData(0x09); // no def
    sendData(0x00); // def 00==

    sendCmd(ILI9341_CMD_Negative_Gamma_Correction);
    sendData(0x00); // def 08
    sendData(0x0E); // no def
    sendData(0x14); // no def
    sendData(0x03); // def 07
    sendData(0x11); // no def
    sendData(0x07); // def 05
    sendData(0x31); // no def
    sendData(0xC1); // no def
    sendData(0x48); // no def
    sendData(0x08); // def 04
    sendData(0x0F); // no def
    sendData(0x0C); // def 0f
    sendData(0x31); // no def
    sendData(0x36); // no def
    sendData(0x0F); // def 0f==

    sendCmd(ILI9341_CMD_Sleep_Out);
    HAL_Delay(120);

    sendCmd(ILI9341_CMD_Display_ON);
}
