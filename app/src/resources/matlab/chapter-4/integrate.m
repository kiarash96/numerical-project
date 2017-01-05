function [intValue] = integrate(func, startV, endV, h, method, addr)

% plot
func = strcat('@(x)', func);
f = str2func(func);
figure('Visible','off');

xs = [startV-1:0.01:endV+1];
ys = arrayfun(f, xs);

h = plot(xs, ys);
hold on;

ax = gca;
ax.XAxisLocation = 'origin';
ax.YAxisLocation = 'origin';
ax.Box = 'off';

saveas(h,addr, 'jpg');

intValue = 10;
end
