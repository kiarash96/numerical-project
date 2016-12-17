function [] = CurveFittingPlot(X, Y, func, address)
    str = '@(x)';
    func = strcat(str, func);
    f = str2func(func);

    xs = [min(X)-1:0.01:max(X)+1];
    ys = zeros(1, length(xs));
    for i = [1:length(xs)]
        ys(i) = f(xs(i));
    end

    figure('Visible','off');

    h = plot(xs, ys);
    hold on;
    scatter(X, Y);

    saveas(h,address, 'jpg');
end

