/* Generated by CIL v. 1.3.7 */
/* print_CIL_Input is true */

#line 1 "nested3.c"
int main(void) 
{ int a ;
  int b ;
  int c ;

  {
#line 2
  a = 6;
#line 3
  b = 6;
#line 4
  c = 6;
#line 7
  a = 0;
  {
#line 7
  while (1) {
    while_0_continue: /* CIL Label */ ;
#line 7
    if (a < 6) {

    } else {
      goto while_0_break;
    }
#line 8
    b = 0;
    {
#line 8
    while (1) {
      while_1_continue: /* CIL Label */ ;
#line 8
      if (b < 6) {

      } else {
        goto while_1_break;
      }
#line 9
      c = 0;
      {
#line 9
      while (1) {
        while_2_continue: /* CIL Label */ ;
#line 9
        if (c < 6) {

        } else {
          goto while_2_break;
        }
#line 9
        c = c + 1;
      }
      while_2_break: /* CIL Label */ ;
      }
#line 8
      b = b + 1;
    }
    while_1_break: /* CIL Label */ ;
    }
#line 7
    a = a + 1;
  }
  while_0_break: /* CIL Label */ ;
  }
#line 14
  if (a == 6) {
#line 14
    if (b == 6) {
#line 14
      if (c == 6) {

      } else {
        goto ERROR;
      }
    } else {
      goto ERROR;
    }
  } else {
    ERROR: 
    goto ERROR;
  }
#line 17
  return (1);
}
}
