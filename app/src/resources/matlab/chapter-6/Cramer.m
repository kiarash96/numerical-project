function [fail , message, history , final ] = Cramer(A,b)
fail =0;
message = '';
x =0;
%defining history structure in this method
history = zeros( size(A, 2)+1, 1);
history(1,1) = det(A);
for i=1:size(A, 2)
    D = A;
    D(:, i) = b;
    history(i+1,1) = det(D); % from  elemnt 2 to i+1  of history are D1 ro Di 
    x(i,1) =  det(D)/ det(A);
end
final = x;
end