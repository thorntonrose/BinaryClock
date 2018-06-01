Binary Clock -- by T. Rose, A. Rose, W. Odom

Description:

   Binary Clock (BINCLOCK) is a novelty clock program that displays the time
   in binary format. Additionally, it moves around the screen at random
   intervals, so that it can be used as a screen saver (granted, one that is
   manually started).

   BINCLOCK looks like this:

     ษอออออออออออออปฤฤฤ grey border        ฤฤษอออออออออออออป
   1ฤืฤ     ฤืฤฤฤ red or yellow dots ฤฤืฤ     ฤืฤ1
   2ฤืฤ         บ                         บ          ฤืฤ2
   4ฤืฤ        บ                         บ        ฤืฤ4
   8ฤืฤ           บ                         บ          ฤืฤ8
     ศอออออออออออออผ     optional numbers ฤฤฤืฤ1 7 5 9 5 9 บ
       H H M M S S                           ศอออออออออออออผ

Usage:

   binclock [?] [NUM] [12HR]

   ?    - Print the usage message
   NUM  - Display numbers
   12HR - Display time in 12-hour format instead of 24-hour format

   The default operation is to display without numbers, in 24-hour mode. In
   24-hour mode, the binary time display is always in red. In 12-hour mode,
   the binary time display is in yellow during the AM hours and red during the
   PM hours.

   When the clock is running, press ESC to stop.


Notes:

   1. The screen colors and movement are done with ANSI escape codes. Make
      sure that you have the ANSI.SYS driver (or an equivalent) loaded before
      you try to run BINCLOCK. It should not crash if you don't have ANSI.SYS
      loaded, but it will sure be ugly.

   2. The EXE included in the archive was built from the C version of the
      source code, since it produces the smallest file and has the most
      options.

   3. The C version was written using MIX Power C. Basically, the code is ANSI
      C compliant, but I did break down and use a couple of the MIX library
      functions (like curson).

   4. The FoxPro version of BINCLOCK was actually the first (believe it or
      not). I have included it because there are some interesting aspects of
      the code and because it is such a weird program to even write in Fox.
      When I wrote it, I just wanted to see if I could build it with the
      screen generator.

   5. The Ada version is included for the language junkies. Note that it is
      incomplete. The author, April Rose, could not provide the source code
      for all the packages that she used, since she had access to them as a
      library only.


Credits:

   Credit for BINCLOCK goes to the following:

      Thornton Rose - FoxPro version and C version
      April Rose    - Ada version
      Bill Odom     - Suggestions and other help

   Please feel free to contact me, Thornton Rose, if you have any questions
   or comments. I can be reached at the following:

   Mail: Thornton Rose
         3376 Pine Forest Rd.
         Cantonment, FL 32533

   Phone: 904-479-3082

   Compuserve: 71203,276


Copyright:

   Binary Clock (BINCLOCK) is freeware. You may use, copy, and distribute it
   free of charge, which means that you don't have provide any monetary
   compensation to the authors. However, credit is always appreciated.


Disclaimer:

   Binary Clock (BINCLOCK) is not warranted. Use it at your own risk. If using
   BINCLOCK or any part of BINCLOCK causes damage to your computer, any other
   computer, or any machine on which you run BINCLOCK; causes you to lose any
   data, programs, or property; gives you headaches; or causes anything even
   slighty bad to happen to you or to any of your friends, enemies, relatives,
   pets, furniture, automobiles, or other entities, properties, or things to
   whom you might in any manner be related or that you might posses, the
   authors of BINCLOCK cannot and will not be responsible or liable.
