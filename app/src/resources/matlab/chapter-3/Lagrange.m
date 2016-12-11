
function [y , flag] = Lagrange( X1 , Y1, x1 , m1)
format long
  X = str2double(X1);
  Y = str2double(Y1);
  x = str2double(x1);
  m = str2double(m1);
  n1 = length(X);
  n2 = length(Y);
  flag = 1;
  digits(m);
  if (n1 ~= n2)
      flag =0;
      y = 'Error: Input Size Not Matching';
  elseif (isempty(X))
      flag = 0;
      y = 'Error : Enter a Numeric Array for X';

  elseif(isempty(Y))
      flag = 0;
      y = 'Error : Enter a Numeric Array for Y';
 
  elseif(isempty(x))
      flag = 0;
      y = 'Error : Enter a Numeric Array for x';

  elseif(isempty(m))
      flag = 0;
      y = 'Error : Enter a Numeric Array for Number of Floating Point Digits';
     
  else
  n = n1;
  l = ones(n);
  y= 0;
  for i = 1 : n
      for j = 1: n 
          if( j  ~= i )
              temp = (x - X(j))/(X(i) - X(j));
              temp = vpa(temp);
              l(i) = l(i) *temp ; 
              l(i) = vpa(l(i));
             
          end
      end
      y = y + vpa(Y(i) * l(i));
  end
  figure
  plot(X , Y  , 'b--o' , x , y , 'g--o' );
  title('Interpolated Curve Using Lagrange Method');
  xlabel('X values');
  ylabel('Y values');
  legend('given values' , 'interpolated value');
  end
 
end


