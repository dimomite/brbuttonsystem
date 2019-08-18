/*
 * ili9341commands.h
 *
 *  Created on: 17 авг. 2019 г.
 *      Author: DiMomite
 */

#ifndef ILI9341COMMANDS_H_
#define ILI9341COMMANDS_H_


const int ILI9341_CMD_NOOP = 0x00;
const int ILI9341_CMD_Software_Reset = 0x01;
const int ILI9341_CMD_Sleep_Out = 0x11;
const int ILI9341_CMD_Display_Inversion_OFF = 0x20;
const int ILI9341_CMD_Display_Inversion_ON = 0x21;
const int ILI9341_CMD_Column_Address_Set = 0x2a;
const int ILI9341_CMD_Page_Address_Set= 0x2b;
const int ILI9341_CMD_Memory_Write= 0x2c;
const int ILI9341_CMD_Memory_Access_Control = 0x36;
const int ILI9341_CMD_COLMOD_Pixel_Format_Set  = 0x3a;
const int ILI9341_CMD_Gamma_Set = 0x26;
const int ILI9341_CMD_Display_OFF = 0x28;
const int ILI9341_CMD_Display_ON = 0x29;
//const int ILI9341_CMD_ = 0x;
//const int ILI9341_CMD_ = 0x;


const int ILI9341_CMD_Power_Control_1 = 0xc0;
const int ILI9341_CMD_Power_Control_2 = 0xc1;
const int ILI9341_CMD_VCOM_Control_1 = 0xc5;
const int ILI9341_CMD_VCOM_Control_2 = 0xc7;
const int ILI9341_CMD_Frame_Rate_Control_In_Normal_Mode_Full_Colors = 0xb1;
const int ILI9341_CMD_Display_Function_Control = 0xb6;
const int ILI9341_CMD_Positive_Gamma_Correction  = 0xe0;
const int ILI9341_CMD_Negative_Gamma_Correction  = 0xe1;
//const int ILI9341_CMD_ = 0x;


// Extend register commands
const int ILI9341_CMD_Power_Control_A = 0xcb;
const int ILI9341_CMD_Power_Control_B = 0xcf;
const int ILI9341_CMD_Driver_Timing_Control_A = 0xe8;
const int ILI9341_CMD_Driver_Timing_Control_B = 0xea;
const int ILI9341_CMD_Power_On_Sequence_Control = 0xed;
const int ILI9341_CMD_Pump_Ratio_Control = 0xf7;
const int ILI9341_CMD_Enable_3G = 0xf2;
//const int ILI9341_CMD_ = 0x;
//const int ILI9341_CMD_ = 0x;
//const int ILI9341_CMD_ = 0x;
//const int ILI9341_CMD_ = 0x;
//const int ILI9341_CMD_ = 0x;

#endif /* ILI9341COMMANDS_H_ */
