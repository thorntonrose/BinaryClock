------------------------------------------------------------------------------
-- Filename      : BINCLOCK.ADA
-- Author        : April Rose
-- creation date : September 21, 1992
-- Description   : This program places a binary clock on the screen.  It is
--    read like any other clock in the form of HHMMSS. Each column represents
--    a binary number with the least significant bit at the top of the column.
------------------------------------------------------------------------------

with p_calend;             -- get the time
with p_char; use p_char;   -- xstring and xcharacter habit
with p_color;              -- window colors
with p_kbdio;              -- check for a keystroke
with p_sys;                -- bit set checks
with unsigned;             -- byte variables, conversions
with p_window;             -- what you see

procedure Binclock is

   time_len : constant := 6;

   type time_type is array(1..4) of xstring(1..time_len);

   window       : p_window.window_handle;
   location     : p_window.win_coord_type := (10,25);

   frame_color  : p_color.color_type := (p_color.high_white, p_color.black);
   high_color   : p_color.color_type := (p_color.white, p_color.black);
   term_key     : xcharacter;

   kbchar       : xcharacter := nul;    -- key from keyboard buffer

------------------------------------------------------------------------------
-- This procedure builds the time array for the current time.

   function build_time_array return time_type is

      current_time : p_calend.time := p_calend.clock;
      time         : time_type;
      hhmmss       : xstring(1..time_len) := p_calend.HOUR(current_time) & 
                                             p_calend.MINUTE(current_time) & 
                                             p_calend.SECOND(current_time);
      temp : unsigned.byte;  -- holds the current segment of HHMMSS

   begin

      for I in 1..time_len loop
         temp := unsigned.byte(integer'value(to_string(hhmmss(i..i))));
         for j in time'first..time'last loop
            if p_sys.bit_set(temp, j-1) then
               time(j)(i) := p_char.eot;
            else
               time(j)(i) := p_char.space;
            end if;
         end loop;
      end loop;

      return time;

   end build_time_array;

------------------------------------------------------------------------------
-- This procedure spaces out the line to give the clock a wider look.

   procedure put_formatted_string(window : in out p_window.window_handle;
                                  line   : in     xstring) is

      formatted_line : xstring(1..14) := (others => ' ');

   begin

      for i in 1..line'length loop
         formatted_line(i*2) := line(i);
      end loop;
      p_window.put_string(window, formatted_line);

   end put_formatted_string;

------------------------------------------------------------------------------
-- This procedure gets the current time in binary form and places the string
-- in the window.

   procedure update_clock is

      time_array : time_type;

   begin

      time_array := build_time_array;

      for i in 1..4 loop
         p_window.set_row_col(window, i, 2);
         put_formatted_string(window, time_array(i));
      --   p_window.put_string(window, time_array(i));
      end loop;

   end update_clock;

------------------------------------------------------------------------------

begin  -- BINCLOCK

   -- open a window for the clock.
   p_window.open_window( 
      handle            => window,   
      upper_left_corner => location,
      pane_height       => 4,
      pane_width        => 14,
      header            => "Binary Clock",
      frame             => p_window.double_frame,
      line_scroll       => true,  
      pane_scroll       => false,
      frame_color       => frame_color,
      pane_color        => high_color,
      cursor_on         => false );

   -- if there is a key waiting in the buffer then exit, else update
   -- the clock.
   loop
      p_kbdio.get_key_no_wait( kbchar );

      if kbchar /= p_char.nul then
         exit;
      end if;

      update_clock;
   end loop;

   -- restore the screen
   p_window.close_window(window);

end Binclock;


