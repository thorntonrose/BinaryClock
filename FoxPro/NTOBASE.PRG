* ntobase.prg
*
parameters b, x

private m.d, m.r, m.s

if ! between(b, 2, 35)
   return ""
endif

m.d = x
m.s = ""
   
do while .t.

   m.r = m.d % b
   
   m.s = iif(m.r >= 10, ;
             chr(asc("A") - 10 + m.r), ;
             alltrim(str(m.r))) + ;
         m.s

   m.d = int(int(m.d) / int(b))

   if m.d == 0
      exit
   endif
            
enddo   

return m.s

* end ntobase.prg
