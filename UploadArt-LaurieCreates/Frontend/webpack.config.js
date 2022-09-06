const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');


module.exports = {
  optimization: {
    usedExports: true
  },
  entry: {
    homePage: path.resolve(__dirname, 'src', 'pages', 'homePage.js'),
    createPage: path.resolve(__dirname, 'src', 'pages', 'createPage.js'),
    updatePage: path.resolve(__dirname, 'src', 'pages', 'updatePage.js'),
    viewPage: path.resolve(__dirname, 'src', 'pages', 'viewPage.js'),
    deletePage: path.resolve(__dirname, 'src', 'pages', 'deletePage.js')
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].js',
  },

  devServer: {
    https: false,
    port: 8080,
    open: true,
    openPage: 'http://localhost:8080/homepage.html',
    // diableHostChecks, otherwise we get an error about headers and the page won't render
    disableHostCheck: true,
    contentBase: 'packaging_additional_published_artifacts',
    // overlay shows a full-screen overlay in the browser when there are compiler errors or warnings
    overlay: true,
    proxy: [
       {
         context: [
           '/artwork'
         ],
         target: 'http://localhost:5001'
       }
     ]
   },

  plugins: [
    new HtmlWebpackPlugin({
      template: './src/homepage.html',
      filename: 'homepage.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/createformpage.html',
      filename: 'createformpage.html',
      inject: false
    }),
   new HtmlWebpackPlugin({
       template: './src/updateFormPage.html',
       filename: 'updateFormPage.html',
       inject: false
    }),
   new HtmlWebpackPlugin({
       template: './src/viewArtworkPage.html',
       filename: 'viewArtworkPage.html',
       inject: false
    }),
   new HtmlWebpackPlugin({
       template: './src/deleteArtworkPage.html',
       filename: 'deleteArtworkPage.html',
       inject: false
    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/css'),
          to: path.resolve("dist/css")
        }
      ]
    }),
    new CleanWebpackPlugin()   
  ]
}
