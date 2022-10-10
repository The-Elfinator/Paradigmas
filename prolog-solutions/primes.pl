divid(N, K, 0) :- 0 is mod(N, K), K >= 2.
divid(N, K, R) :- \+(0 is mod(N, K)), K >= 2, K1 is K-1, divid(N, K1, R).
divid(N, 1, 1). 

concat([], B, B).
concat([H | T], B, [H | R]) :- concat(T, B, R).

prime(N) :- N >= 4, N1 is floor(sqrt(N)) + 1, divid(N, N1, R1), 1 is R1.
prime(2).
prime(3).

composite(N) :- N > 1, \+(prime(N)).

find_dividers(N, K, R, Ret) :- N > 1, K >= 2, K =< N, prime(K), 0 is mod(N, K), concat(R, [K], R1), N1 is N / K, find_dividers(N1, K, R1, Ret), !.
find_dividers(N, K, R, Ret) :- N > 1, K >= 2, K =< N, \+(prime(K), 0 is mod(N, K)), K1 is K + 1, find_dividers(N, K1, R, Ret), !.
find_dividers(1, K, R, R).
find_unique_dividers(N, K, R, Ret) :- N > 1, K >= 2, K =< floor(sqrt(N)), 0 is mod(N, K), X is N / K, \+(X = K), prime(K), prime(X), concat(R, [K, X], R1), K1 is K + 1, find_unique_dividers(N, K1, R1, Ret), !.
find_unique_dividers(N, K, R, Ret) :- N > 1, K >= 2, K =< floor(sqrt(N)), 0 is mod(N, K), X is N / K, X = K, prime(K), concat(R, [K], R1), K1 is K + 1, find_unique_dividers(N, K1, R1, Ret), !.
find_unique_dividers(N, K, R, Ret) :- N > 1, K >= 2, K =< floor(sqrt(N)), 0 is mod(N, K), X is N / K, X = K, composite(K), K1 is K + 1, find_unique_dividers(N, K1, R, Ret), !.
find_unique_dividers(N, K, R, Ret) :- N > 1, K >= 2, K =< floor(sqrt(N)), 0 is mod(N, K), X is N / K, \+(X = K), prime(K), composite(X), concat(R, [K], R1), K1 is K + 1, find_unique_dividers(N, K1, R1, Ret), !.
find_unique_dividers(N, K, R, Ret) :- N > 1, K >= 2, K =< floor(sqrt(N)), 0 is mod(N, K), X is N / K, \+(X = K), composite(K), prime(X), concat(R, [X], R1), K1 is K + 1, find_unique_dividers(N, K1, R1, Ret), !.
find_unique_dividers(N, K, R, Ret) :- N > 1, K >= 2, K =< floor(sqrt(N)), 0 is mod(N, K), X is N / K, \+(X = K), composite(K), composite(X), K1 is K + 1, find_unique_dividers(N, K1, R, Ret), !.
find_unique_dividers(N, K, R, Ret) :- N > 1, K >= 2, K =< floor(sqrt(N)), \+(0 is mod(N, K)), K1 is K + 1, find_unique_dividers(N, K1, R, Ret), !.
find_unique_dividers(N, K, R, R) :- N > 1, K > floor(sqrt(N)).

foldLeft([], Z, _, Z).
foldLeft([H | T], Z, F, R) :- G =.. [F, Z, H, RH], call(G), foldLeft(T, RH, F, R).
mul(A, B, R) :- R is A*B.

prime_divisors(1, []).
prime_divisors1(N, []).
prime_divisors1(N, [H | T]) :- 0 is mod(N, H), prime(H), prime_divisors1(N, T).
prime_divisors(N, [H | T]) :- N > 1, list([H | T]), foldLeft([H | T], 1, mul, N), prime_divisors1(N, [H | T]).
prime_divisors(N, [N]) :- N > 1, prime(N). 
prime_divisors(N, R) :- \+(list(R)), N > 1, composite(N), find_dividers(N, 2, [], R). 

unique_prime_divisors(1, []).
unique_prime_divisors1(N, []).
unique_prime_divisors1(N, [H | T]) :- 0 is mod(N, H), prime(H), unique_prime_divisors1(N, T).
unique_prime_divisors(N, [H | T]) :- N > 1, list([H | T]), unique_prime_divisors1(N, [H | T]).
unique_prime_divisors(N, [N]) :- N > 1, prime(N).
unique_prime_divisors(N, R) :- N > 1, \+(list(R)), composite(N), find_unique_dividers(N, 2, [], R).