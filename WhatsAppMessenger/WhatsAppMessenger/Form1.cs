using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using WhatsAppApi.Account;
using WhatsAppApi;
using System.Threading;

namespace WhatsAppMessenger
{
    public partial class Form1 : Form
    {
        WhatsApp wa;
        WhatsUser user;

        public Form1()
        {
            InitializeComponent();
        }

        public static string Base64Encode(string text)
        {
            return System.Convert.ToBase64String(System.Text.Encoding.UTF8.GetBytes(text));
        }

        private delegate void UpdateTextBox(TextBox textbox, string value);

        public void UpdateDataTextBox(TextBox textbox, string value)
        {
            textbox.Text += value;
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void btnSend_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(txtMessage.Text))
            {
                return;
            }
            if (wa != null)
            {
                if (wa.ConnectionStatus == ApiBase.CONNECTION_STATUS.LOGGEDIN)
                {
                    wa.SendMessage(txtTo.Text, txtMessage.Text);
                    txtStatus.Text += string.Format("\r\n{0}:{1}", user.Nickname, txtMessage.Text);
                    txtMessage.Clear();
                    txtMessage.Focus();
                }
            }
        }

        private void btnLogin_Click(object sender, EventArgs e)
        {
            WhatsUserManager manager = new WhatsUserManager();
            user = manager.CreateUser(txtPhone.Text, txtName.Text);
            var thread = new Thread(t =>
            {
                UpdateTextBox textBox = UpdateDataTextBox;
                wa = new WhatsApp(txtPhone.Text, Base64Encode(txtPassword.Text), txtName.Text, true);
                wa.OnConnectSuccess += () =>
                {
                    if (txtStatus.InvokeRequired)
                        Invoke(textBox, txtStatus, "Connected...");
                    wa.OnLoginSuccess += (phone, data) =>
                    {
                        if (txtStatus.InvokeRequired)
                        {
                            Invoke(textBox, txtStatus, "\r\nLogin success !");
                        }
                        while (wa != null)
                        {
                            wa.PollMessages();
                            Thread.Sleep(200);
                            continue;
                        }
                    };
                    wa.OnGetMessage += (node, from, id, name, message, receipt_sent) =>
                    {
                        if (txtStatus.InvokeRequired)
                        {
                            Invoke(textBox, txtStatus, string.Format("\r\n{0}:{1}", name, message));
                        }
                    };
                    wa.OnLoginFailed += (data) =>
                    {
                        if (txtStatus.InvokeRequired)
                        {
                            Invoke(textBox, txtStatus, string.Format("\r\nLogin failed: {0}", data));
                        }
                    };

                    wa.Login();
                };
                wa.OnConnectFailed += (ex) =>
                {
                    if (txtStatus.InvokeRequired)
                    {
                        Invoke(textBox, txtStatus, string.Format("\r\nConnect failed: {0}", ex.StackTrace));
                    }
                };
                wa.Connect();
            })
            { IsBackground = true };
            thread.Start();
        }

        private void label2_Click(object sender, EventArgs e)
        {

        }
    }
}
