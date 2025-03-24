package com.tomasmacri.accessibilitybasicviews.repository

import com.tomasmacri.accessibilitybasicviews.domain.model.Coin

object CoinRepository {

    private val coinsList = mutableListOf(
        Coin("Bitcoin", "BTC", "Bitcoin es la primera criptomoneda descentralizada y la más conocida a nivel mundial. Fue creado por una persona o grupo bajo el pseudónimo de Satoshi Nakamoto en 2008. Su objetivo es permitir pagos de igual a igual sin la necesidad de una autoridad central.", 46000, "https://cryptologos.cc/logos/bitcoin-btc-logo.png", isFavorite = true),
        Coin("Ethereum", "ETH", "Ethereum es una plataforma descentralizada de código abierto que ejecuta contratos inteligentes. A diferencia de Bitcoin, Ethereum permite la creación de aplicaciones descentralizadas (dApps) sobre su blockchain, lo que lo hace mucho más versátil.", 3300, "https://cryptologos.cc/logos/ethereum-eth-logo.png"),
        Coin("Litecoin", "LTC", "Litecoin es una criptomoneda que fue lanzada en 2011 como una alternativa más ligera y rápida a Bitcoin. Se basa en un código abierto y permite la verificación de transacciones con mayor rapidez.", 150, "https://cryptologos.cc/logos/litecoin-ltc-logo.png", isFavorite = true),
        Coin("Bitcoin Cash", "BCH", "Bitcoin Cash es una criptomoneda que nació como un fork de Bitcoin en 2017. El objetivo de Bitcoin Cash es permitir transacciones más rápidas y más baratas, a diferencia de Bitcoin.", 500, "https://cryptologos.cc/logos/bitcoin-cash-bch-logo.png", isFavorite = true),
        Coin("Cardano", "ADA", "Cardano es una plataforma de blockchain de tercera generación que se centra en la escalabilidad, la sostenibilidad y la interoperabilidad de las criptomonedas. Su criptomoneda nativa es ADA.", 1, "https://cryptologos.cc/logos/cardano-ada-logo.png"),
        Coin("Binance Coin", "BNB", "Binance Coin es la criptomoneda nativa de Binance, el mayor intercambio de criptomonedas. BNB se utiliza para pagar tarifas de transacción en Binance, entre otros usos.", 400, "https://cryptologos.cc/logos/binance-coin-bnb-logo.png", isFavorite = true),
        Coin("Solana", "SOL", "Solana es una plataforma blockchain de alto rendimiento que se enfoca en velocidad y escalabilidad. Está diseñada para ser más rápida y económica que otras plataformas como Ethereum.", 150, "https://cryptologos.cc/logos/solana-sol-logo.png"),
        Coin("Dogecoin", "DOGE", "Dogecoin es una criptomoneda que comenzó como una broma inspirada en un meme de internet. Sin embargo, ha ganado una gran comunidad y aceptación gracias a su naturaleza amigable y transacciones rápidas.", 0, "https://cryptologos.cc/logos/dogecoin-doge-logo.png"),
        Coin("Chainlink", "LINK", "Chainlink es un servicio de oráculos que conecta contratos inteligentes con datos externos. Utiliza su propia criptomoneda LINK para recompensar a los operadores de nodos que proporcionan datos fiables.", 30, "https://cryptologos.cc/logos/chainlink-link-logo.png", isFavorite = true),
        Coin("Stellar", "XLM", "Stellar es una plataforma blockchain diseñada para facilitar las transferencias de dinero a través de fronteras. Su criptomoneda, Lumens (XLM), se utiliza para realizar pagos rápidos y de bajo costo.", 1, "https://cryptologos.cc/logos/stellar-xlm-logo.png", isFavorite = true),
        Coin("VeChain", "VET", "VeChain es una plataforma de blockchain diseñada para mejorar la gestión de la cadena de suministro. Se utiliza principalmente en la logística y el seguimiento de productos en diversas industrias.", 0, "https://cryptologos.cc/logos/vechain-vet-logo.png", isFavorite = true),
        Coin("TRON", "TRX", "TRON es una plataforma blockchain descentralizada que tiene como objetivo crear un sistema de entretenimiento de contenido digital descentralizado, con el objetivo de que los creadores de contenido sean dueños de sus trabajos.", 5, "https://cryptologos.cc/logos/tron-trx-logo.png"),
        Coin("Monero", "XMR", "Monero es una criptomoneda centrada en la privacidad que utiliza criptografía avanzada para ofrecer transacciones completamente anónimas. A diferencia de Bitcoin, las transacciones de Monero son completamente privadas y no trazables.", 300, "https://cryptologos.cc/logos/monero-xmr-logo.png", isFavorite = true),
        Coin("EOS", "EOS", "EOS es una plataforma de blockchain de alta velocidad y escalabilidad. Está diseñada para permitir aplicaciones descentralizadas y contratos inteligentes con un enfoque en la facilidad de uso y el rendimiento.", 12, "https://cryptologos.cc/logos/eos-eos-logo.png"),
        Coin("IOTA", "IOTA", "IOTA es una criptomoneda diseñada específicamente para el Internet de las Cosas (IoT). Utiliza una tecnología de registro distribuido llamada Tangle, que no depende de bloques tradicionales, lo que permite realizar transacciones sin comisiones.", 2, "https://cryptologos.cc/logos/iota-iota-logo.png"),
        Coin("Zcash", "ZEC", "Zcash es una criptomoneda centrada en la privacidad que utiliza pruebas de conocimiento cero para garantizar transacciones privadas y seguras. Es conocida por su énfasis en la seguridad y el anonimato de las transacciones.", 170, "https://cryptologos.cc/logos/zcash-zec-logo.png", isFavorite = true),
        Coin("Aave", "AAVE", "Aave es una plataforma de finanzas descentralizadas (DeFi) que permite a los usuarios prestar y pedir prestado activos digitales sin necesidad de intermediarios. Aave utiliza su token nativo AAVE para gobernanza y recompensas.", 350, "https://cryptologos.cc/logos/aave-aave-logo.png", isFavorite = true),
        Coin("Uniswap", "UNI", "Uniswap es un protocolo de intercambio descentralizado (DEX) basado en Ethereum. Permite a los usuarios intercambiar criptomonedas de manera directa sin la necesidad de un intermediario centralizado.", 20, "https://cryptologos.cc/logos/uniswap-uni-logo.png"),
        Coin("PancakeSwap", "CAKE", "PancakeSwap es un exchange descentralizado (DEX) basado en Binance Smart Chain (BSC). Similar a Uniswap, permite a los usuarios intercambiar criptomonedas de manera rápida y sin comisiones altas.", 10, "https://cryptologos.cc/logos/pancakeswap-cake-logo.png", isFavorite = true),
        Coin("Aave", "AAVE", "Aave es una plataforma de finanzas descentralizadas (DeFi) que permite a los usuarios pedir prestado y prestar criptomonedas. Su token AAVE se utiliza para la gobernanza y la seguridad de la red.", (100..400).random(), "https://cryptologos.cc/logos/aave-aave-logo.png"),
        Coin("Maker", "MKR", "MakerDAO es un sistema de préstamos descentralizados basado en Ethereum. El token MKR es utilizado para la gobernanza del sistema y para asegurar la estabilidad de la moneda estable DAI.", (1000..3000).random(), "https://cryptologos.cc/logos/maker-mkr-logo.png"),
        Coin("SushiSwap", "SUSHI", "SushiSwap es un intercambio descentralizado basado en Ethereum, similar a Uniswap. Ofrece intercambios de tokens y una plataforma de yield farming, todo gestionado por su token SUSHI.", (5..30).random(), "https://cryptologos.cc/logos/sushiswap-sushi-logo.png"),
        Coin("Shiba Inu", "SHIB", "Shiba Inu es una criptomoneda que comenzó como una parodia de Dogecoin. Con una comunidad activa, SHIB ha crecido como una 'meme coin' pero también está trabajando en varios proyectos como ShibaSwap.", (0..0).random(), "https://cryptologos.cc/logos/shiba-inu-shib-logo.png", isFavorite = true),
        Coin("Avalanche", "AVAX", "Avalanche es una plataforma blockchain de alta velocidad y bajo costo. Se enfoca en ofrecer transacciones rápidas y escalabilidad para aplicaciones descentralizadas y activos digitales.", (30..100).random(), "https://cryptologos.cc/logos/avalanche-avax-logo.png")
    )

    fun getCoins(): List<Coin> {
        return coinsList.toList()
    }

    fun deleteCoin(coin: Coin): List<Coin> {
        coinsList.removeIf { it.code == coin.code }
        return getCoins()
    }

    fun updateCoin(coin: Coin): List<Coin> {
        coinsList[coinsList.indexOfFirst { it.code == coin.code }] = coin
        return coinsList.toList()
    }

    fun addCoin(coin: Coin): Boolean {
        if (coinsList.none { it.code == coin.code }) {
            return coinsList.add(coin)
        }
        return false

    }

    fun findCoinByCode(code: String): Coin? = coinsList.firstOrNull { it.code == code }
}