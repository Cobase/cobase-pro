import React, { Component } from 'react';

export default class CobaseApp extends Component {
  render() {
    return (
      <div>
        <h1>
          CobasePRO
        </h1>

        {this.props.children}
      </div>
    );
  }

  componentDidMount() {

  }
}
