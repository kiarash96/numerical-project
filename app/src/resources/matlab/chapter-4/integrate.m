function [result] = integrate(func, startV, endV, h, method, addr)

func = strcat('@(x)', func);
f = str2func(func);

if (method == 0)
    [xs, ys, intValue] = trapezoidal(f, startV, endV, h);
    result = strcat(mat2str(xs), '\\', mat2str(ys), '\\', num2str(intValue));
end

% plot
figure('Visible','off');

xs = [startV-1:0.01:endV+1];
ys = arrayfun(f, xs);

h = plot(xs, ys);
hold on;
fill(xs, ys, 'r');

ax = gca;
ax.XAxisLocation = 'origin';
ax.YAxisLocation = 'origin';
ax.Box = 'off';

saveas(h,addr, 'jpg');

end
