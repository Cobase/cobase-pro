import React, { Component } from 'react';
import Sidebar from './Sidebar';

export default class LoggedIn extends Component {
  render() {
    const { children, currentUser, onLogout } = this.props;

    return (
      <div className="logged-in">
        <Sidebar onLogout={onLogout} />

        {children && React.cloneElement(children, {
          currentUser
        })}
      </div>
    );
  }
}