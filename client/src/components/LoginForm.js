import React, { Component, PropTypes } from 'react';

export default class LoginForm extends Component {
  constructor(props) {
    super(props);

    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleSubmit(e) {
    e.preventDefault();

    this.props.onLogin(
      this.refs.username.value,
      this.refs.password.value
    );
  }

  render() {
    return (
      <div>
        <h3>Enter your credentials</h3>
        <form onSubmit={this.handleSubmit}>
          <label>Username:</label>
          <input type="text" ref="username" />
          <label>Password:</label>
          <input type="password" ref="password" />
          <button type="submit">Login</button>
        </form>
      </div>
    );
  }
}

LoginForm.propTypes = {
  onLogin: PropTypes.func.isRequired,
};
