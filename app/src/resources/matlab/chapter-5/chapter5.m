function [ ansX , ansY, ansZ ] = chapter5( equation1 , equation2 , h , x0 , y0 ,t0 ,  method , step )
%UNTITLED9 Summary of this function goes here
%   Detailed explanation goes here
ansX = [];
ansY = [];
ansZ = [];

method
switch method
    case 1
        [ ansX , ansY ] = Taylor( equation1 , x0, y0 , h , step );
    case 2
        [ ansX , ansY ] = Euler ( equation1 , x0, y0, h , step);
    case 3
        [ ansX , ansY ] = Midpoint_RungeKutta( equation1 , x0, y0, h , step );
    case 4
        [ ansX , ansY ] = Third_RungeKutta( equation1 , x0, y0, h , step );
    case 5
        [ ansX , ansY ] = Four_RungeKutta( equation1 , x0, y0, h , step );
    case 6
        [ ansX , ansY, ansZ ] = DE_Euler( equation1 ,equation2 ,  x0, y0, t0 ,h , step );
    case 7
        [ ansX , ansY, ansZ ] = DE_RungeKutta( equation1 ,equation2 ,  x0, y0, t0 ,h , step);
end

