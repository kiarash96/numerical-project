function y = checkNotReal(A)
y = 0;
if (isreal(A) ==0 ||sum( isinf(A))>0 ||sum( isnan(A))>0 )
y = 1;
end
end
