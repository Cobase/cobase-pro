import React, { Component } from 'react';

export default class LoggedOut extends Component {
  render() {
    const { children, onLogin } = this.props;

    return (
      <div className="logged-out">
        {children && React.cloneElement(children, {
          onLogin
        })}
      </div>
    );
  }
}
