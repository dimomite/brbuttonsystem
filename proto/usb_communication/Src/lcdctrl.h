/*
 * lcdctrl.h
 *
 *  Created on: Aug 15, 2019
 *      Author: DiMomite
 */

#ifndef LCDCTRL_H_
#define LCDCTRL_H_

#include <stdint.h>

#ifndef RGB
#define RGB(r, g, b)  ((((r) & 0xf8) << 8) | (((g) & 0xfc) << 3) | (((b) & 0xf8) >> 3)) // TODO should be in general tools
#endif

typedef enum {
    LCD_ORIENTATION_UNDEFINED = -1,
    LCD_ORIENTATION_VERTICAL = 1,
    LCD_ORIENTATION_HORIZONTAL = 2,
    LCD_ORIENTATION_VERTICAL_INVERTER = 3,
    LCD_ORIENTATION_HORIZONTAL_INVERTER = 4,

    LCD_ORIENTATION_DEFAULT = LCD_ORIENTATION_HORIZONTAL
} LcdOrientation;

typedef struct {
    LcdOrientation orientation; // TODO check, probably not needed
    uint16_t width;
    uint16_t height;
} LcdCtrl;

void LCD_Init(LcdCtrl* lcdctrl);
void LCD_ChangeRotation(LcdCtrl* lcdctrl, LcdOrientation orientation);
void LCD_SetArea(LcdCtrl* lcdctrl, uint16_t x1, uint16_t y1, uint16_t x2, uint16_t y2);
void LCD_FillWithColor(LcdCtrl* lcdctrl, uint16_t color, uint32_t pixelsCount); // FIXME bad API

void LCD_DrawFilledRectangle(LcdCtrl* lcdctrl, uint16_t x1, uint16_t y1, uint16_t x2, uint16_t y2, uint16_t color);
void LCD_DrawPixel(LcdCtrl* lcdctrl, uint16_t x, uint16_t y, uint16_t color);

void LCD_ChangeInversionMode(LcdCtrl* lcdctrl, const uint8_t isEnable);

void LCD_ReadId(LcdCtrl* lcdctrl, uint32_t* id);

#endif /* LCDCTRL_H_ */
