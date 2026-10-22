export default async function handler(req, res) {
  if (req.method !== "POST") {
    return res.status(405).json({
      error: "متد درخواست پشتیبانی نمی‌شود."
    });
  }

  const apiKey = process.env.GROQ_API_KEY;

  if (!apiKey) {
    return res.status(500).json({
      error: "کلید Groq در تنظیمات Vercel ثبت نشده است."
    });
  }

  try {
    const body = req.body || {};
    const message =
      typeof body.message === "string" ? body.message.trim() : "";

    if (!message) {
      return res.status(400).json({
        error: "پیام خالی است."
      });
    }

    const history = Array.isArray(body.history)
      ? body.history
          .filter(
            (item) =>
              item &&
              ["user", "assistant"].includes(item.role) &&
              typeof item.content === "string"
          )
          .slice(-12)
          .map((item) => ({
            role: item.role,
            content: item.content.slice(0, 6000)
          }))
      : [];

    const messages = [
      {
        role: "system",
        content:
          "تو Rovia هستی، یک دستیار هوش مصنوعی دوستانه، دقیق و مفید. به زبان کاربر پاسخ بده. اگر جواب چیزی را نمی‌دانی، صادقانه بگو."
      },
      ...history,
      { role: "user", content: message }
    ];

    const response = await fetch(
      "https://api.groq.com/openai/v1/chat/completions",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${apiKey}`
        },
        body: JSON.stringify({
          model: "llama-3.3-70b-versatile",
          messages,
          temperature: 0.7,
          max_tokens: 1500
        })
      }
    );

    const data = await response.json().catch(() => ({}));

    if (!response.ok) {
      console.error("Groq API error:", response.status, data);
      return res.status(502).json({
        error: "پاسخ‌گویی هوش مصنوعی ناموفق بود.",
        details:
          data?.error?.message ||
          `خطای سرویس هوش مصنوعی (${response.status})`
      });
    }

    const reply = data?.choices?.[0]?.message?.content;

    if (typeof reply !== "string" || !reply.trim()) {
      return res.status(502).json({
        error: "پاسخ معتبری دریافت نشد."
      });
    }

    return res.status(200).json({ reply: reply.trim() });
  } catch (error) {
    console.error("Rovia API error:", error);

    return res.status(500).json({
      error: "خطای داخلی سرور Rovia رخ داد."
    });
  }
}
