import React, { Component, PropTypes } from 'react';

export default class LoginForm extends Component {
  render() {
    return (
      <div>
        <h3>Enter your credentials</h3>
        <form onSubmit={this.handleSubmit.bind(this)}>
          <label>Username:</label>
          <input type="text" ref="username" />
          <label>Password:</label>
          <input type="password" ref="password" />
          <button type="submit">Login</button>
        </form>
      </div>
    );
  }

  handleSubmit(e) {
    e.preventDefault();

    this.props.onLogin(
      this.refs.username.value,
      this.refs.password.value
    );
  }
}

LoginForm.propTypes = {
  onLogin: PropTypes.func.isRequired
};
