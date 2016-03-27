/* binclock.c
 *
 * Created: 31-Oct-93
 * Updated: 02-Nov-93
 * Author : Thornton Rose
 * Credits: April Rose (Ada version)
 *          Bill Odom (help and suggestions)
 */

/* Binary clock
 *
 *  Without numbers:        With numbers:
 *  ษอออออออออออออป         ษอออออออออออออป
 *  บ       บ         บ       บ
 *  บ       บ         บ       บ
 *  บ       บ         บ       บ
 *  บ       บ         บ       บ
 *  ศอออออออออออออผ         บ 1 2 3 4 5 6 บ
 *                          ศอออออออออออออผ
 */

#include "binclock.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <dos.h>


static void draw_clock_box(int, int, int);
static void draw_clock_time(char*, int, int, int);


/* main:
 *
 * Here's where it starts. :-)
 */
int main(int argc, char *argv[])
{
   int          clock_row;
   int          clock_column;
   int          clock_size       = CLOCK_HEIGHT;
   int          clock_is_12hour  = 0;
   int          display_numbers  = 0;
   int          seconds_for_move;
   time_t       current_time;
   time_t       move_time;
   char         clock_string[9]  = "";
   int          done = 0;


   /* Check for command line arguments.
    */
   if ( (argc > 1) && (strcmp(argv[1], "?") == 0) )
   {
      printf("BinClock -- by T. Rose, A. Rose, W. Odom\n\n");
      printf("Usage:\n");
      printf("   binclock [?] [NUM] [12HR]\n\n");
      printf("Notes:\n");
      printf("   The default operation is to display without numbers, in 24-hour mode. In\n");
      printf("   24-hour mode, the binary time display is always in red. In 12-hour mode,\n");
      printf("   the binary time display is in yellow during the AM hours and red during the\n");
      printf("   PM hours.\n\n");
      printf("   When the clock is running, press ESC to stop.\n");

      exit(1);
   }

   /* Parse command line arguments.
    */
   while (*++argv)
   {
      if (strcmp(*argv, "NUM") == 0)
      {
         clock_size      = CLOCK_HEIGHT + 1;
         display_numbers = 1;
      }

      if (strcmp(*argv, "12HR") == 0)
      {
         clock_is_12hour = 1;
      }
   }

   /* Set the cursor off and set the display to normal.
    */
   cursoff();
   ansi_set_display("0");

   /* Seed the random number generator with the current time.
    */
   srand((unsigned) time(&current_time));

   /* Loop until done, which will be when the ESC key is hit.
    */
   while (! done)
   {
      ansi_cls();

      clock_row        = irand(1, 25 - clock_size);
      clock_column     = irand(1, 80 - CLOCK_WIDTH);
      seconds_for_move = irand(1, 60);

      draw_clock_box(clock_row, clock_column, display_numbers);
      time(&move_time);

      for(;;)
      {
         if (getkey() == ESC)
         {
            done = 1;
            break;
         }

         time(&current_time);

         if (clock_is_12hour)
         {
            hms12time(clock_string, current_time);
         }
         else
         {
            hmstime(clock_string, current_time);
         }

         draw_clock_time(clock_string,
                         clock_row,
                         clock_column,
                         display_numbers);

         if (difftime(time(&current_time), move_time) >= seconds_for_move)
         {
            break;
         }

         sleep(0.5);
      }
   }

   /* Set the display back to white on black, clear the screen,
    * and turn on the cursor.
    */
   ansi_set_display("0;37;40");
   ansi_cls();
   curson();
}


/* draw_clock_box:
 *
 * Draw the clock box at the given row and column.
 */
void draw_clock_box(int row, int column, int numbers_on)
{
   /* Set color to grey on black.
    */
   ansi_set_display("1;30;40");

   /* Draw clock box.
    */
   ansi_printf_at(row,     column, "ษอออออออออออออป");
   ansi_printf_at(row + 1, column, "บ             บ");
   ansi_printf_at(row + 2, column, "บ             บ");
   ansi_printf_at(row + 3, column, "บ             บ");
   ansi_printf_at(row + 4, column, "บ             บ");

   if (numbers_on)
   {
      ansi_printf_at(row + 5, column, "บ             บ");
      ansi_printf_at(row + 6, column, "ศอออออออออออออผ");
   }
   else
   {
      ansi_printf_at(row + 5, column, "ศอออออออออออออผ");
   }
}


/* draw_clock_time:
 *
 * Draw the given time in binary and decimal formats in the clock
 * box, which is positioned at the given row and column.
 */
void draw_clock_time(char* clock_string,
                     int clock_row,
                     int clock_column,
                     int numbers_on)
{
   int i, j, k;


   if (clock_string[6] == 'A')
   {
      /* Set color to yellow on black.
       */
      ansi_set_display("1;33;40");
   }
   else
   {
      /* Set color to red on black.
       */
      ansi_set_display("0;31;40");
   }

   /* Draw binary time.
    */
   for (i = 6; i > 0 ; i--)
   {
      for (j = 0, k = 1; j < 4; j++, k *= 2)
      {
         ansi_putchar_at(clock_row + 1 + j,
                         clock_column + (i * 2),
                         ( (clock_string[(i - 1)] - '0') & k ? '' : ' ' ) );
      }
   }

   if (numbers_on)
   {
      /* Set color to white on black.
       */
      ansi_set_display("0;37;40");

      /* Draw time digits.
       */
      for (i = 0; i < 6; i++)
      {
         ansi_putchar_at(clock_row + 5,
                         clock_column + ((i + 1) * 2),
                         clock_string[i]);
      }
   }
}

/* end binclock.c */
