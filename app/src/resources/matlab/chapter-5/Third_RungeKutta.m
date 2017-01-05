function [ ansX , ansY ] = Third_RungeKutta( equation , iniX, iniY, h , n )
f = inline(equation , 'x' , 'y');
ansX(1) = iniX;
ansY(1) = iniY;

for i = 1:n
   k1(i) = f(ansX(i) , ansY(i));
   k2(i) = f(ansX(i)+ h/2 , ansY(i)+h *k1(i)/2);
   k3(i) = f(ansX(i)+ h , ansY(i)+h *(k2(i)*2 - k1(i)));
   ansX(i+1) = ansX(i)+ h;
   ansY(i+1) = ansY(i)+ h/6 *(k1(i) + 4* k2(i) + k3(i));
end
end
