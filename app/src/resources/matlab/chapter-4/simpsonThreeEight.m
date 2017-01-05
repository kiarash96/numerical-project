function [intValue] = simpsonThreeEight(xs, ys, h)
    n = length(xs);
    if (n < 2)
        intValue = 0;
    else
        intValue = 3 * h / 8 * (ys(1) + ys(n) + 2 * sum(ys(4:3:n-3)) + 3 * (sum(ys(2:3:n-2)) + sum(ys(3:3:n-1))));
    end
end

