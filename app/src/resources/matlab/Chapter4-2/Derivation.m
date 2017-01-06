function y  = Derivation( func , degree, x , order, h )
f = inline(func , 'x');
t = sym(func);
g = diff(t , degree);
fn = inline(g);
switch degree
    case 1
        
        err(1) = abs(ThreePoint(func,x,h)-fn(x));
        err(2) = abs(CentralDifference( func , x , degree, order, h )-fn(x));
        if(err(1)<err(2))
            y = ThreePoint(func,x,h);
        else 
            y = CentralDifference( func , x , degree, order, h );
        end
    otherwise
        
           y  = CentralDifference( func , x , degree, order, h );
end        
end

