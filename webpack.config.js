var webpack = require('webpack');

module.exports = {
    cache: true,
    entry: './app/assets/js/cobase.js',
    output: {
        filename: './public/dist/js/bundle.js',
        publicPath: '/assets/'
    },
    module: {
        loaders: [
            {
                test: /\.js$/,
                loader: 'jsx-loader'
            },
            {test: /\.jsx$/, loader: 'jsx-loader'},
            {
                test: /\.less$/,
                loaders: [
                    'style-loader',
                    'css-loader',
                    'autoprefixer-loader?browsers=last 2 version',
                    'less-loader'
                ]
            },
            {
                test: /\.css$/,
                loader: 'style-loader!css-loader'
            },
            {
                test: /\.(jpe?g|png|gif)$/i,
                loaders: [
                    'file?hash=sha512&digest=hex&name=webpack-assets/images/[hash:base58:8].[ext]',
                    'image-webpack?bypassOnDebug&optimizationLevel=7&interlaced=false'
                ]
            },
            {
                test: /\.(woff|woff2)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                loader: 'url-loader?limit=10000&mimetype=application/font-woff&name=webpack-assets/fonts/[name].[ext]'
            },
            {
                test: /\.(ttf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                loader: 'file-loader?name=webpack-assets/fonts/[name].[ext]'
            }
        ]
    },
    plugins: [
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery",
            "window.jQuery": "jquery"
        })
    ]
};