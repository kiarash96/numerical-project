function [intValue] = simpsonOneThird(xs, ys, h)
    n = length(xs);
    if (n < 2)
        intValue = 0;
    else
        intValue = h/3 * (ys(1) + 4 * sum(ys(2:2:n-1)) + 2 * sum(ys(3:2:n-2)) + ys(n));
    end
end
