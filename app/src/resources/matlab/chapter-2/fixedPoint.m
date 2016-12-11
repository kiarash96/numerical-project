function [ roots, values ,message, fail] = fixedPoint(func,p0, step)

    str = '@(x)';
    func = strcat(str, func);
    f = str2func(func);

    fail =0;
    message = '';
    roots=0;
    valueInRoots=0;
    
    i = 1;
    roots(1) = p0;
    valueInRoots (1) = f(p0);
    
    
    while i <= step
       roots(i+1) = f(roots(i));
       i = i+1;
    end

    values = zeros(step);
end