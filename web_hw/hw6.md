





$\frac {\partial } {\partial \omega_j}y_ilog\sigma(\omega^Tx_i)\\=y_i \frac 1 {\sigma(\omega^Tx_i)}\frac {\partial \sigma(\omega^T x_i)}{\partial \omega_j}\\=y_i \frac 1 {\sigma(\omega^Tx_i)} \frac {\partial \sigma(\omega^Tx_i)}{\partial (\omega^Tx_i)}\frac {\partial (w^Tx_i)}{\partial \omega_j}\\=y_i \frac 1 {\sigma(\omega^Tx_i)} \sigma(\omega^Tx_i)(1-\sigma(\omega^Tx_i))x_{ij}\\=y_ix_{ij}(1-\sigma(\omega^Tx_i))$



$\frac {\partial } {\partial \omega_j}(1-y_i)log(1-\sigma(\omega^Tx_i))\\=(1-y_i)\frac 1 {1-\sigma(\omega^Tx_i)}*(-1)*\frac {\partial \sigma(\omega^T x_i)}{\partial \omega_j}\\=-(1-y_i)x_{ij}\sigma(\omega^Tx_i)$



$\frac {\partial} {\partial {\omega_j}}J(\omega)\\=-\frac {\partial} {\partial_{\omega_j}}\Big[\sum \limits_{i=1}^Ny_ilog \sigma(\omega^Tx_i)+（1-y_i)log(1-\sigma(\omega^Tx_i))\Big]\\=\sum\limits_{i=1}^N\big[(1-y_i)x_{ij}\sigma(\omega^Tx_i)-y_ix_{ij}(1-\sigma(\omega^Tx_i))\big]\\=\sum\limits_{i=1}^Nx_{ij}(\sigma(\omega^Tx_i)-y_i)$