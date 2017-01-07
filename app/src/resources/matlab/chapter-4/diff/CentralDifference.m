function output = CentralDifference( func , x , degree, order, h )
    f = inline(func);
    switch order
        case 2
            switch degree
                case 1
                    output = (f(x+h) - f(x-h))/(2*h);
                case 2
                    output = (f(x+h)-2*f(x)+f(x-h))/(h^h);
                case 3
                    output = (f(x+2*h)-2*f(x+h)+2*f(x-h)-f(x-2*h))/(2*h^3);
                case 4
                    output = (f(x+2*h)-4*f(x+h)+6*f(x)-4*f(x-h)+f(x-2*h))/(h^4);
            end
        case 4
            switch degree
                case 1
                    output = (-f(x+2*h)+8*f(x+h)-8*f(x-h)+f(x+2*h))/(12*h);
                case 2
                    output = (-f(x+2*h)+16*f(x+h)-30*f(x)+16*f(x-h)-f(x-2*h))/(12*h^2);
                case 3
                    output = (-f(x+3*h)+8*f(x+2*h)-13*f(x+h)+13*f(x-h)-8*f(x-2*h)+f(x-3*h))/(8*h^3);
                case 4
                    output  = (-f(x+3*h)+12*f(x+2*h)-39*f(x+h)+56*f(x)-39*f(x-h)+12*f(x-2*h)+f(x-3*h))/(6*h^4);
            end
    end

end

