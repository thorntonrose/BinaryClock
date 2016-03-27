/* binclock.h
 *
 * Created: 31-Oct-93
 * Updated: 02-Nov-93
 * Author : Thornton Rose
 */

#ifndef BINCLOCK_H
#define BINCLOCK_H

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define CLOCK_WIDTH      15
#define CLOCK_HEIGHT     6

#define ESC  27
#define CSI  "\x1b["  /* Control-sequence initiator ESC+[ */

#define ansi_cls(void)            printf(CSI "2J")
#define ansi_set_display(s)       printf(CSI "%sm", s)
#define ansi_move_to(r, c)        printf(CSI "%d;%dH", r, c)
#define ansi_printf_at(r, c, s)   printf(CSI "%d;%dH%s", r, c, s)
#define ansi_putchar_at(r, c, x)  printf(CSI "%d;%dH%c", r, c, x)

#define hmstime(s, t)    strftime(s, (size_t) 8, "%H%M%S", (struct tm *) localtime(&t))
#define hms12time(s, t)  strftime(s, (size_t) 8, "%I%M%S%p", (struct tm *) localtime(&t))

#define irand(i, j)  ((rand() % (j - i + 1)) + i)

#endif

/* end binclock.h */
