var path = require('path');
var express = require('express');
var webpack = require('webpack');
var config = require('../webpack.config');

var app = express();
var compiler = webpack(config);

var devMiddleware = require('webpack-dev-middleware')(compiler, {
  noInfo: true,
  publicPath: config.output.publicPath
});

app.use(devMiddleware);

app.use(require('webpack-hot-middleware')(compiler));

app.get('*', function(req, res) {
  res.end(
    devMiddleware.fileSystem.readFileSync(
      path.join(config.output.path, 'index.html')
    )
  );
});

app.listen(3000, 'localhost', function(err) {
  if (err) {
    console.log(err);
    return;
  }

  console.log('Listening at http://localhost:3000');
});
