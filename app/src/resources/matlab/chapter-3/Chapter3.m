function [ flag, result ] = Chapter3( X1, Y1 , x1 , iOrC ,type,  m1 )
format long
  X = str2double(X1);
  Y = str2double(Y1);
  x = str2double(x1);
  n2 = length(Y);
  flag = 1;
  if (n1 ~= n2)
      flag =0;
      result = 'Error: Input Size Not Matching';
  elseif (isempty(X))
      flag = 0;
      result = 'Error : Enter a Numeric Array for X';

  elseif(isempty(Y))
      flag = 0;
      result = 'Error : Enter a Numeric Array for Y';
 
  elseif(isempty(x))
      flag = 0;
      result = 'Error : Enter a Numeric Array for x';

  elseif(isempty(m))
      flag = 0;
      result = 'Error : Enter a Numeric Array for Number of Floating Point Digits';
     
  else
      if(iOrC == 0)
         if(type == 1)
            [flag , result]  = Lagrange(X ,Y , x , m);
         elseif(type == 2)
            [flag , result]  = newtonDividedDifference(X , Y , x , m);
         elseif(type ==3 ) 
            [flag , result] = newtonForwardBackward(X , Y , x , m);
         else
              flag = 0;
              result = 'Invalid Type';
         end
      else
          [flag , result] = CurveFitting(X1 , Y1 , m , type) ;
      end
   end
end

