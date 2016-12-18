function [ roots, values ,message, fail] = fixedPoint(func,p0, step)

    str = '@(x)';
    func = strcat(str, func);
    f = str2func(func);

    realf = str2func(strcat(func, '-x'));

    fail =0;
    message = '';
    roots=0;
    valueInRoots=0;
    
    i = 1;
    roots(1) = p0;
    valueInRoots (1) = realf(p0);
    
    
    while i <= step
       roots(i+1) = f(roots(i));
       valueInRoots(i + 1) = realf(roots(i + 1));
       i = i+1;
    end

    values = valueInRoots;
end