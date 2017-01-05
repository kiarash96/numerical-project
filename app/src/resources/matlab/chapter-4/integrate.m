function [result, error] = integrate(func, a, b, h, method, addr)

func = strcat('@(x)', func);
f = str2func(func);

n = (b - a) / h;
xs = [a:h:b];
ys = arrayfun(f, xs);

error = 0;

if (method == 0)
    intValue = trapezoidal(xs, ys, h);
elseif (method == 1)
    if (mod(n, 2) == 1)
        error = 1;
        result = '\text{N must be even!}';
    else
        intValue = simpsonOneThird(xs, ys, h);
    end
elseif (method == 2)
    if (mod(n, 3) ~= 0)
        error = 1;
        result = '\text{N must be a multiple of 3}';
    else
        intValue = simpsonThreeEight(xs, ys, h);
    end
elseif (method == 5)
    intValue = customSimpson(xs, ys, h);
elseif (method == 3)
    result = romberg(f, a, b, n);
end

% create result string if no errors occured.
if (error == 0)
    if (method <= 2 || method == 5)
        result = strcat(mat2str(xs), '\\', mat2str(ys), '\\', num2str(intValue));
    end
end

% plot
figure('Visible','off');

xs = [a-1:0.01:b+1];
ys = arrayfun(f, xs);

h = plot(xs, ys);
hold on;
%fill(xs, ys, 'r');

ax = gca;
ax.XAxisLocation = 'origin';
ax.YAxisLocation = 'origin';
ax.Box = 'off';

saveas(h,addr, 'jpg');

end