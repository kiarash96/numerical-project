function [intValue] = trapezoidal(xs, ys, h)
    n = length(xs);
    intValue = h/2 * (2 * sum(ys) - ys(1) - ys(n));
end

