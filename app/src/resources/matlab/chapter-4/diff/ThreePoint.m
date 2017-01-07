function y = ThreePoint( func, x , h )
f = inline(func);
y = (3*f(x+h)-4*f(x)+f(x-h))/(2*h);

end

