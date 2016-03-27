* IRAND.PRG
*
* This program returns a uniformly distributed integer between i and j.

FUNCTION irand

   PARAMETERS i, j

   RETURN int((j - i + 1) * rand() + i)

* end IRAND.PRG
